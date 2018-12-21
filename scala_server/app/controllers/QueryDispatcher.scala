package controllers

import javax.inject.Inject

import play.api.mvc._
import ControllerUtils._

class QueryDispatcher @Inject()(hQueryController: HQueryController,
                                // mQueryController: MQueryController,
                                cc: ControllerComponents)
  extends AbstractController(cc) {

  case class ReqGet(code: Option[String], timestamp: Option[String], engine: Option[String])

  case class ReqScan(code: Option[String], start: Option[String], end: Option[String], engine: Option[String])

  def extractReqGet(request: Request[AnyContent]): ReqGet = {
    val codeOption = request.getQueryString("code")
    val timestampOption = request.getQueryString("timestamp")
    val engineOption = request.getQueryString("engine")

    ReqGet(codeOption, timestampOption, engineOption)
  }

  def extractReqScan(request: Request[AnyContent]): ReqScan = {
    val codeOption = request.getQueryString("code")
    val startOption = request.getQueryString("start")
    val endOption = request.getQueryString("end")
    val engineOption = request.getQueryString("engine")

    ReqScan(codeOption, startOption, endOption, engineOption)
  }

  def get: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      extractReqGet(request) match {
        case ReqGet(Some(code), Some(timestamp), Some("hbase")) => {
          hQueryController.get(code, timestamp)
        }

        case ReqGet(Some(code), Some(timestamp), Some("mongo")) => {
          hQueryController.get(code, timestamp)
        }
        // Unrecognized request format
        case _ => Ok(UnrecognizedRequest)
      }
    }
  }


  def scan: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      extractReqScan(request) match {
        case ReqScan(Some(code), Some(start), Some(end), Some("hbase")) => {
          hQueryController.scan(code, start, end)
        }

        case ReqScan(Some(code), Some(start), Some(end), Some("mongo")) => {
          hQueryController.scan(code, start, end)
        }
        // Unrecognized request format
        case _ => Ok(UnrecognizedRequest)
      }
    }
  }
}

