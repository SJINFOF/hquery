package services


object HBaseDBSpec {

  import Utils._

  def main(args: Array[String]): Unit = {
    val db = new HBaseDB()

    val get = db.dbGet(Utils.exampleRowKey)

    val (size, duration) = timed {
      get.size
    }

    println(s"Got $size records")
    println(s"Duration: $duration ms")

    val scan = db.dbScan(Utils.exampleStartRowKey, Utils.exampleEndRowKey)
    val (size1, duration1) = timed {
      scan.size
    }

    println(s"Got $size1 records")
    println(s"Duration: $duration1 ms")
  }
}
