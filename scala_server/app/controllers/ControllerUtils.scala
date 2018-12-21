package controllers

import play.api.mvc._
import services.QueryResult

object ControllerUtils {
  def response(errcode: Int, msg: String, queryTime: Long, count: Long, data: Iterator[String]): String = {
    val dataStr = if (data.isEmpty) {
      "[]"
    } else {

      data.mkString("[", ",", "]")
    }
    s"""{"errcode":$errcode,"msg":"$msg","queryTime":$queryTime,"count":$count,"data":$dataStr}"""
  }

  val UnrecognizedRequest: String = response(-1, "Unrecognized request format.", 0, 0, QueryResult.EmptyStringIterator)
}
