package controllers.api
import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Basic,BasicForm}
import play.api.data.FormError

import services.FormService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BasicFormController  @Inject()(cc: ControllerComponents, formService: FormService) extends AbstractController(cc){
  implicit val basicFormat = Json.format[Basic]

  def getAll() = Action.async { implicit request: Request[AnyContent] =>
    formService.listAllItems map { items =>
      Ok(Json.toJson(items))
    }
  }

  def getById(company_id:String,emp_id: String) = Action.async { implicit request: Request[AnyContent] =>
    formService.getItem(company_id,emp_id) map { item =>
      Ok(Json.toJson(item))
    }
  }

  def add() = Action.async { implicit request: Request[AnyContent] =>
    BasicForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => {
        errorForm.errors.foreach(println)
        Future.successful(BadRequest("Error!"))
      },
      data => {
        val newTodoItem = Basic(data.id, data.company_id,data.emp_id, data.age)
        formService.addItem(newTodoItem).map( _ => Redirect(routes.BasicFormController.getAll()))
      })
  }

  def update(company_id:String,emp_id: String) = Action.async { implicit request: Request[AnyContent] =>
    BasicForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => {
        errorForm.errors.foreach(println)
        Future.successful(BadRequest("Error!"))
      },
      data => {
        val todoItem = Basic(data.id, data.company_id,data.emp_id, data.age)
        formService.updateItem(todoItem).map( _ => Redirect(routes.BasicFormController.getAll()))
      })
  }
  def getByCompanyId(company_id:String) = Action.async { implicit request: Request[AnyContent] => {
    formService.getByCompanyId(company_id) map { item =>
      Ok(Json.toJson(item))
    }
  }
  }

  def delete(company_id:String,emp_id: String) = Action.async { implicit request: Request[AnyContent] =>
    formService.deleteItem(company_id,emp_id) map { res =>
      Redirect(routes.BasicFormController.getAll())
    }
  }
}
