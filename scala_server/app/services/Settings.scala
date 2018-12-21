package services

object Settings {
  val HBaseHost = "head"
  val HBasePort = "20005"
  val HBaseScanBatchSize = 100000
  val HBaseTableName = "hisdata30g"

  val MongoUri = "mongodb://head:20001"
  val MongoDatabaseName = "szinfo"
  val MongoCollectionName = "hisdata30g"
}
