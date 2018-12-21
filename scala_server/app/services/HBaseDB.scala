package services

import javax.inject.Inject

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import scala.collection.JavaConverters._

class HBaseDB @Inject() extends DB {
  val tableName: TableName = TableName.valueOf(Settings.HBaseTableName)

  def dbGet(rowKey: String, fetchResult: Boolean): QueryResult = {
    println(s"Get option: $rowKey, fetchResult: $fetchResult")

    val table = HBaseDB.connection.getTable(this.tableName)
    val get = new Get(Bytes.toBytes(rowKey))

    val (queryResult, duration) = Utils.time {
      val result = table.get(get)
      Bytes.toString(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("raw")))
    }
    table.close()

    if (queryResult == null) {
      QueryResult(Array[String](), 0, duration)
    } else if (!fetchResult) {
      QueryResult(Array[String](), 1, duration)
    } else {
      QueryResult(Array(queryResult), 1, duration)
    }
  }

  def dbScan(startRowKey: String, endRowKey: String, fetchResult: Boolean): QueryResult = {
    println(s"Scan option: $startRowKey to $endRowKey, fetchResult: $fetchResult")

    val table = HBaseDB.connection.getTable(this.tableName)
    val scan = new Scan()
      .withStartRow(Bytes.toBytes(startRowKey))
      .withStopRow(Bytes.toBytes(endRowKey))
      .setBatch(Settings.HBaseScanBatchSize)

    val (queryResultList, duration) = Utils.time {
      val scanner = table.getScanner(scan)
      scanner.asScala.map {
        result => Bytes.toString(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("raw")))
      }
    }
    table.close()

    if (queryResultList == null) {
      QueryResult(Array[String](), 0, duration)
    } else if (!fetchResult) {
      QueryResult(Array[String](), queryResultList.size, duration)
    } else {
      QueryResult(queryResultList, queryResultList.size, duration)
    }
  }
}

object HBaseDB {
  val conf: Configuration = HBaseConfiguration.create()
  conf.set("hbase.zookeeper.quorum", Settings.HBaseHost)
  conf.set("hbase.zookeeper.property.clientPort", Settings.HBasePort)

  /**
    * Since there already exists a thread pool in hbase connection implementation
    * and the connection object is thread safe,
    * we share the connection in all threads
    */
  val connection: Connection = ConnectionFactory.createConnection(conf)
}