package services


import scala.concurrent.Await
import scala.concurrent.duration.Duration

object MongoDBSpec {

  import MongoDB._

  def main(args: Array[String]): Unit = {
    val db = new MongoDB()

    val get = db.dbGet(Utils.exampleRowKey, fetchResult = false)
    println(s"Got ${get.count} records")
    println(s"Duration: ${get.time} ms")

    val scan = db.dbScan(Utils.exampleStartRowKey, Utils.exampleEndRowKey, fetchResult = false)
    println(s"Got ${scan.count} records")
    println(s"Duration: ${scan.time} ms")

    val a= db.dbScanAsync(Utils.exampleStartRowKey, Utils.exampleEndRowKey, fetchResult = false)
    val s = System.currentTimeMillis()
    a.onComplete(x => println(System.currentTimeMillis() - s))

    Await.result(a, Duration.Inf)
  }
}
