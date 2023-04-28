package controllers

import models.BasicForm

import javax.inject._
import play.api._
import play.api.mvc._

import play.api.libs.json._
import models.{Basic,BasicForm}
import play.api.data.FormError

import services.FormService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
//@Singleton
//class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
//
//  /**
//   * Create an Action to render an HTML page.
//   *
//   * The configuration in the `routes` file means that this method
//   * will be called when the application receives a `GET` request with
//   * a path of `/`.
//   */
//  def index() = Action { implicit request: Request[AnyContent] =>
//    Ok(views.html.index())
//  }
//}
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def index() = Action {
    Ok("ok")
  }
}


