package controllers

import javax.inject._

import play.api.mvc._
import services.{HBaseDB, MongoDB, QueryResult}

class QueryController @Inject()(hbase: HBaseDB, mongo: MongoDB, cc: ControllerComponents)
  extends AbstractController(cc) {

  case class ReqGet(code: Option[String], timestamp: Option[String], engine: Option[String])

  case class ReqScan(code: Option[String], start: Option[String], end: Option[String], engine: Option[String])

  def get: Action[AnyContent] = internalGet(true)

  def scan: Action[AnyContent] = internalScan(true)

  def benchmarkGet: Action[AnyContent] = internalGet(false)

  def benchmarkScan: Action[AnyContent] = internalScan(false)

  def internalGet(fetchResult: Boolean): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      val codeOption = request.getQueryString("code")
      val timestampOption = request.getQueryString("timestamp")
      val engineOption = request.getQueryString("engine")

      ReqGet(codeOption, timestampOption, engineOption) match {
        // HBase query request
        case ReqGet(Some(code), Some(timestamp), Some("hbase")) => {
          val QueryResult(data, count, duration) = hbase.get(code, timestamp, fetchResult)
          response(0, "ok", queryTime = duration, count = count, data = data)
        }
        // Mongo query request
        case ReqGet(Some(code), Some(timestamp), Some("mongo")) => {
          val QueryResult(data, count, duration) = mongo.get(code, timestamp, fetchResult)
          response(0, "ok", queryTime = duration, count = count, data = data)
        }
        // Unrecognized request format
        case _ => UnrecognizedRequest
      }
    }
  }

  def internalScan(fetchResult: Boolean): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      val codeOption = request.getQueryString("code")
      val startOption = request.getQueryString("start")
      val endOption = request.getQueryString("end")
      val engineOption = request.getQueryString("engine")


      ReqScan(codeOption, startOption, endOption, engineOption) match {
        // HBase query request
        case ReqScan(Some(code), Some(start), Some(end), Some("hbase")) => {
          val QueryResult(data, count, duration) = hbase.scan(code, start, end, fetchResult)
          response(0, "ok", queryTime = duration, count = count, data = data)
        }
        // Mongo query request
        case ReqScan(Some(code), Some(start), Some(end), Some("mongo")) => {
          val QueryResult(data, count, duration) = mongo.scan(code, start, end, fetchResult)
          response(0, "ok", queryTime = duration, count = count, data = data)
        }
        // Unrecognized request format
        case _ => UnrecognizedRequest
      }
    }
  }

  def response(errcode: Int, msg: String, queryTime: Long, count: Long, data: Iterable[String]): Result = {
    val dataStr = if (data.isEmpty) {
      "[]"
    } else {
      data.mkString("[", ",", "]")
    }
    val responseStr = s"""{"errcode":$errcode,"msg":"$msg","queryTime":$queryTime,"count":$count,"data":$dataStr}"""
    Ok(responseStr).as("application/json")
  }

  val UnrecognizedRequest: Result = response(-1, "Unrecognized request format.", 0, 0, Array[String]())
}
