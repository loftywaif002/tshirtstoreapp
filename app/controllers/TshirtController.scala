package controllers

import javax.inject._
import models.{SchemaDefinition, Tshirt}
import play.api.libs.json.{JsObject, Json}
import play.api.mvc._
import sangria.renderer.SchemaRenderer

import scala.collection.mutable.ListBuffer

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TshirtController  @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

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

  def renderSchema = Action {
    Ok(SchemaRenderer.renderSchema(SchemaDefinition.TshirtSchema))
  }
}
