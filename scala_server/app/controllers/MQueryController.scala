//package controllers
//
//import javax.inject._
//
//import play.api.mvc._
//import services.HBaseDB
//
//
//class MQueryController @Inject()(context: QueryExecutionContextImpl,
//                                 hbase: HBaseDB,
//                                 // mongo: MongoDB,
//                                 cc: ControllerComponents)
//  extends AbstractController(cc) {
//
//
//  def get(code: String, timestamp: String): Action[AnyContent] = Action {
//    implicit request: Request[AnyContent] => {
//      case ReqGet(Some(code), Some(timestamp), Some("hbase")) => {
//        val resultsIterator = hbase.get(code, timestamp)
//        response(0, "ok", queryTime = duration, count = count, data = data)
//      }
//
//      // Mongo query request
//      //        case ReqGet(Some(code), Some(timestamp), Some("mongo")) => {
//      //          val QueryResult(data, count, duration) = mongo.get(code, timestamp, fetchResult)
//      //          response(0, "ok", queryTime = duration, count = count, data = data)
//      //        }
//      // Unrecognized request format
//      case _ => UnrecognizedRequest
//    }
//  }
//
//
//  def scan(code: String, start: String, end: String): Action[AnyContent] = Action {
//    implicit request: Request[AnyContent] => {
//      {
//        // HBase query request
//        case ReqScan(Some(code), Some(start), Some(end), Some("hbase")) => {
//          val QueryResult(data, count, duration) = hbase.scan(code, start, end, fetchResult)
//          response(0, "ok", queryTime = duration, count = count, data = data)
//        }
//        // Mongo query request
//        case ReqScan(Some(code), Some(start), Some(end), Some("mongo")) => {
//          val QueryResult(data, count, duration) = mongo.scan(code, start, end, fetchResult)
//          response(0, "ok", queryTime = duration, count = count, data = data)
//        }
//        // Unrecognized request format
//        case _ => UnrecognizedRequest
//      }
//    }
//  }
//
//}
