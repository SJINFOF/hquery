package services

object Settings {
  val HBASE_HOST = "head"
  val HBASE_PORT = "20005"
  val HBASE_SCAN_BATCHSIZE = 20000
  val HBASE_TABLE_NAME = "hisdata30g"
  val HBASE_TIMEOUT_NS = 200000 // 20s

  val MONGO_URI = "mongodb://head:20001"
  val MONGO_DATABASE_NAME = "szinfo"
  val MONGO_COLLECTION_NAME = "hisdata30g"
}
