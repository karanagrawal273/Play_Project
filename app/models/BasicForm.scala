package models

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import com.google.inject.Inject
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

case class Basic(id:String,company_id:String, emp_id:String,age:Int)

object BasicForm{
  val form = Form(
    mapping(
      "id" -> text,
      "company_id"->text,
      "emp_id" -> text,
      "age" -> number
    )(Basic.apply)(Basic.unapply)
  )
}
class BasicFormDef(tag: Tag) extends Table[Basic](tag, "basicform") {

  def id = column[String]("id", O.PrimaryKey)
  def company_id=column[String]("company_id")
  def emp_id = column[String]("emp_id")
  def age = column[Int]("age")

  override def * = (id,company_id, emp_id, age) <> (Basic.tupled,Basic.unapply)
}
class List @Inject()(
         protected val dbConfigProvider: DatabaseConfigProvider
  )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  var list = TableQuery[BasicFormDef]

  def add(item: Basic): Future[String] = {
    dbConfig.db
      .run(list += item)
      .map(res => "Item successfully added")
      .recover {
        case ex: Exception => {
          printf(ex.getMessage())
          ex.getMessage
        }
      }
  }

  def delete(company_id:String,emp_id: String) = {
    if (list.filter(_.emp_id === emp_id).filter(_.company_id === company_id) == null) {
      println("The company ID or ID are not for the same company")
      dbConfig.db.run(null)
    }
    else {
      dbConfig.db.run(list.filter(_.emp_id === emp_id).filter(_.company_id === company_id).delete)
    }
  }

  def update(item:Basic) = {
    if (list.filter(_.company_id === item.company_id).filter(_.emp_id === item.emp_id) == null) {
      throw new Error("DATA_NOT_FOUND")
    }
    else {
      dbConfig.db
        .run(list.filter(_.emp_id === item.emp_id).filter(_.company_id === item.company_id)
          .map(x => (x.emp_id, x.age))
          .update(item.emp_id, item.age)
        )
    }
  }

  def get(company_id:String,emp_id: String) = {
    if (list.filter(_.emp_id === emp_id).filter(_.company_id === company_id) == null) {
      println("The company ID or ID are not for the same company")
      dbConfig.db.run(null)
    }
    else {
      dbConfig.db.run(list.filter(_.emp_id === emp_id).filter(_.company_id === company_id).result)
    }
  }

  def getByCompanyId(company_id:String) = {
    dbConfig.db.run(list.filter(_.company_id===company_id).result)
  }

  def listAll() = {
    dbConfig.db.run(list.result)
  }
}
