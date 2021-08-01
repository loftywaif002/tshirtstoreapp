package controllers


import akka.actor.ActorSystem
import models._
import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json.{JsObject,Json}
import play.api.mvc._
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.playJson._
import sangria.ast.Document
import sangria.parser.{QueryParser, SyntaxError}
import sangria.renderer.SchemaRenderer

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class TshirtController @Inject()(system: ActorSystem, config: Configuration) extends InjectedController {
  import system.dispatcher
  // return All books
  def index() =  Action { implicit request: Request[AnyContent] =>
    val tshirts = Tshirt.getAllTshirts() // Calling getAllTshirts method on singleton object
    var tshirtList = new ListBuffer[JsObject]()
    for(tshirt <- tshirts){
      val js = Json.obj("id" -> tshirt.id, "color" -> tshirt.color, "url" -> tshirt.pic_url, "price" -> tshirt.price)
      tshirtList += js
    }
    Ok(Json.toJson(tshirtList.toList))
  }


  // GraphQl Query Handler
  def graphql = Action.async(parse.json) { request =>

    val query = (request.body \ "query").as[String]
    print("query=====");
    print(query);
    QueryParser.parse(query) match {
      // query parsed successfully, time to execute it!
      case Success(queryAst) =>
        executeGraphQLQuery(queryAst)

      // can't parse GraphQL query, return error
      case Failure(error: SyntaxError) =>
        Future.successful(BadRequest(Json.obj("error" -> error.getMessage)))
    }
  }

  def executeGraphQLQuery(query: Document) =
    Executor.execute(SchemaDefinition.TshirtSchema, query, new Tshirt())
      .map(Ok(_))
      .recover {
        case error: QueryAnalysisError ⇒ BadRequest(error.resolveError)
        case error: ErrorWithResolver ⇒ InternalServerError(error.resolveError)
      }


  def renderSchema = Action {
    Ok(SchemaRenderer.renderSchema(SchemaDefinition.TshirtSchema))
  }
}
