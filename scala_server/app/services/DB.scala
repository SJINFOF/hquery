package services

trait DB {
  def get(code: String, timestamp: String): Iterator[String] = {
    val rowKey = Utils.rowKey(code, timestamp)
    dbGet(rowKey)
  }

  def scan(code: String, start: String, end: String): Iterator[String] = {
    val startRowKey = Utils.rowKey(code, start, "00001")
    val endRowKey = Utils.rowKey(code, end, "99999")
    dbScan(startRowKey, endRowKey)
  }

  //  def getBenchmark(code: String, timestamp: String): QueryResult = {
  //    val rowKey = Utils.rowKey(code, timestamp)
  //    dbGetBenchmark(rowKey)
  //  }
  //
  //  def scanBenchmark(code: String, start: String, end: String): QueryResult = {
  //    val startRowKey = Utils.rowKey(code, start, "00001")
  //    val endRowKey = Utils.rowKey(code, end, "99999")
  //    dbScanBenchmark(startRowKey, endRowKey)
  //  }

  def dbGet(rowKey: String): Iterator[String]

  def dbScan(startRowKey: String, endRowKey: String): Iterator[String]

  //  def dbGetBenchmark(rowKey: String): QueryResult
  //
  //  def dbScanBenchmark(startRowKey: String, endRowKey: String): QueryResult

}
