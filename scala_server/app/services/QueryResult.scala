package services


object QueryResult {

  /** *
    * We use call-by-name technique to delay the execution of elemProducer
    *
    * @param elemProducer
    */
  class OneElementIterator(elemProducer: => String) extends Iterator[String] {
    var visited = false
    var elemValue: String = null

    override def hasNext: Boolean = !visited

    override def next(): String = {
      if (!visited) {
        visited = true
      }
      elemProducer
    }
  }

  object EmptyStringIterator extends Iterator[String] {
    override def hasNext: Boolean = false

    override def next(): String = null
  }

  def fromElement(elem: => String) = new OneElementIterator(elem)
}