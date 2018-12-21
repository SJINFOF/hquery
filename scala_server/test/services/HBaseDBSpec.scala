package services


object HBaseDBSpec {
  def main(args: Array[String]): Unit = {
    val db = new HBaseDB()

    val get = db.dbGet(Utils.exampleRowKey, fetchResult = false)
    println(s"Got ${get.count} records")
    println(s"Duration: ${get.time} ms")

    val scan = db.dbScan(Utils.exampleStartRowKey, Utils.exampleEndRowKey, fetchResult = false)
    println(s"Got ${scan.count} records")
    println(s"Duration: ${scan.time} ms")
  }
}
