package controllers

import javax.inject._

import akka.stream.scaladsl.Source
import play.api.mvc._
import services.HBaseDB

class HQueryController @Inject()(hbase: HBaseDB,
                                 cc: ControllerComponents)
  extends AbstractController(cc) {

  def get(code: String, timestamp: String) = {
    val resultIter = hbase.get(code, timestamp)
    Ok.chunked(Source.fromIterator(() => resultIter))
  }

  def scan(code: String, start: String, end: String) = {
    val resultIter = hbase.scan(code, start, end)
    Ok.chunked(Source.fromIterator(() => resultIter))
  }
}
