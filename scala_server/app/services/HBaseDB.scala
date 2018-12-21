package services

import javax.inject.Inject

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import scala.collection.JavaConverters._

class HBaseDB @Inject() extends DB {
  val tableName: TableName = TableName.valueOf(Settings.HBASE_TABLE_NAME)

  /** *
    * Query hbase by given row key, the returned iterator's execution is lazy
    *
    * @param rowKey
    * @return
    */
  def dbGet(rowKey: String): Iterator[String] = {

    val table = HBaseDB.connection.getTable(this.tableName)
    val get = new Get(Bytes.toBytes(rowKey))

    // Lazy execute the query operation when needed
    QueryResult.fromElement {
      val result = table.get(get)
      Bytes.toString(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("raw")))
    }
  }

  /** *
    * Scan hbase by given range, the returned iterator's execution is lazy
    *
    * @param startRowKey
    * @param endRowKey
    * @return
    */
  def dbScan(startRowKey: String, endRowKey: String): Iterator[String] = {

    val table = HBaseDB.connection
      .getTableBuilder(this.tableName, null)
      .setOperationTimeout(Settings.HBASE_TIMEOUT_NS)
      .setReadRpcTimeout(Settings.HBASE_TIMEOUT_NS)
      .setRpcTimeout(Settings.HBASE_TIMEOUT_NS)
      .build()

    val scan = new Scan()
      .withStartRow(Bytes.toBytes(startRowKey))
      .withStopRow(Bytes.toBytes(endRowKey))
      .setBatch(Settings.HBASE_SCAN_BATCHSIZE)
      .setCaching(Settings.HBASE_SCAN_BATCHSIZE)

    val scanner = table.getScanner(scan)
    // We wrap the java iterator to scala.
    // This operation is lazy, no real actions happen here.
    scanner.iterator().asScala.map {
      result => Bytes.toString(result.getValue(Bytes.toBytes("data"), Bytes.toBytes("raw")))
    }
  }

}

object HBaseDB {
  val conf: Configuration = HBaseConfiguration.create()
  conf.set("hbase.zookeeper.quorum", Settings.HBASE_HOST)
  conf.set("hbase.zookeeper.property.clientPort", Settings.HBASE_PORT)

  /**
    * Since there already exists a thread pool in Connection implementation
    * and the connection object is thread safe,
    * we just share the connection in all threads
    */
  val connection: Connection = ConnectionFactory.createConnection(conf)
}