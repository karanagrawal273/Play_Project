package services
import com.google.inject.Inject
import models.{Basic, List}

import scala.concurrent.Future
class FormService @Inject()(items: List){
  def addItem(item: Basic): Future[String] = {
    items.add(item)
  }

  def deleteItem(company_id:String,emp_id: String) = {
    items.delete(company_id,emp_id)
  }

  def getByCompanyId(company_id:String)={
    items.getByCompanyId(company_id)
  }

  def updateItem(item: Basic) = {
    items.update(item)
  }

  def getItem(company_id:String,emp_id: String) = {
    items.get(company_id,emp_id)
  }

  def listAllItems() = {
    items.listAll()
  }
}
