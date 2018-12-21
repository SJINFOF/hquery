package services

object Utils {
  val exampleRowKey = "0000112018010309250000001"
  val exampleStartRowKey = "0000112018010309250000001"
  val exampleEndRowKey = "0000112018010309280000001"

  def time[T](f: => T): (T, Long) = {
    val start = System.currentTimeMillis()
    val ret = f
    val end = System.currentTimeMillis()
    (ret, end - start)
  }

  def rowKey(code: String, timestamp: String, suffix: String = "00001") = {
    val sample = "0000112018010309250000001"
    val resolvedTime = timestamp.replace("-", "").replace(":", "")
    val ret = code + resolvedTime + suffix
    require(ret.length == 25, s"Wrong format of row key $ret")
    ret
  }
}
