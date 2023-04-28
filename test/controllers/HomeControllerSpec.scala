package controllers
import play.api.mvc._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with Results {

  "Example Page#index" should {
    "should be valid" in {
      val controller             = new HomeController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.index().apply(FakeRequest(GET,"/"))
      val bodyText: String       = contentAsString(result)
      bodyText mustBe "ok"
    }
  }
}
