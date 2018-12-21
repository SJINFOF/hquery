package services

import javax.inject.Inject

import org.bson.json.{JsonMode, JsonWriterSettings}
import org.mongodb.scala._
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters._

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration


class MongoDB @Inject() extends DB {

  import MongoDB._

  override def dbGet(rowKey: String, fetchResult: Boolean): QueryResult = {
    val filter = equal("CodeStruiDateTime", rowKey)

    val (queryResultList, duration) = Utils.time {
      val future = query(filter)
      Await.result(future, Duration.Inf)
    }

    if (!fetchResult) {
      QueryResult(Array[String](), queryResultList.size, duration)
    } else {
      QueryResult(queryResultList, queryResultList.size, duration)
    }
  }

  override def dbScan(startRowKey: String, endRowKey: String, fetchResult: Boolean): QueryResult = {
    val filter = and(
      gte("CodeStruiDateTime", startRowKey),
      lte("CodeStruiDateTime", endRowKey)
    )

    val (queryResultList, duration) = Utils.time {
      val future = query(filter)
      Await.result(future, Duration.Inf)
    }

    if (!fetchResult) {
      QueryResult(Array[String](), queryResultList.size, duration)
    } else {
      QueryResult(queryResultList, queryResultList.size, duration)
    }
  }

  def dbGetAsync(rowKey: String, fetchResult: Boolean): Future[Iterable[String]] = {
    val filter = equal("CodeStruiDateTime", rowKey)
    query(filter)
  }

  def dbScanAsync(startRowKey: String, endRowKey: String, fetchResult: Boolean): Future[Iterable[String]] = {
    val filter = and(
      gte("CodeStruiDateTime", startRowKey),
      lte("CodeStruiDateTime", endRowKey)
    )
    query(filter)
  }

  def query(filter: Bson): Future[Iterable[String]] = {
    val database: MongoDatabase = MongoDB.client.getDatabase(Settings.MongoDatabaseName)
    val collection = database.getCollection(Settings.MongoCollectionName)
    val future = collection.find(filter).toFuture()
    val queryResultListFuture = future.map {
      resultDocList =>
        resultDocList.map(
          doc => doc.toJson(jsonOption)
        )
    }
    queryResultListFuture
  }
}

object MongoDB {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  val client: MongoClient = MongoClient(Settings.MongoUri)

  val jsonOption: JsonWriterSettings = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()
}
