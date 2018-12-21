package services

trait DB {
  def get(code: String, timestamp: String, fetchResult: Boolean): QueryResult = {
    val rowKey = Utils.rowKey(code, timestamp)
    dbGet(rowKey, fetchResult)
  }

  def scan(code: String, start: String, end: String, fetchResult: Boolean): QueryResult = {
    val startRowKey = Utils.rowKey(code, start, "00001")
    val endRowKey = Utils.rowKey(code, end, "99999")
    dbScan(startRowKey, endRowKey, fetchResult)
  }

  def dbGet(rowKey: String, fetchResult: Boolean): QueryResult

  def dbScan(startRowKey: String, endRowKey: String, fetchResult: Boolean): QueryResult
}
