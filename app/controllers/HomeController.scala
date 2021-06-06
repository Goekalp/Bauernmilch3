package controllers

import play.api.mvc._

import javax.inject.{Inject, _}
import daos.{NutzerDao, ProduktDao}
import model.Produkt
import model.Nutzer
import play.api.data.Form
import play.api.data.Forms.{mapping, number, optional, text}

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(produktDao: ProduktDao, nutzerDao: NutzerDao, controllerComponents: ControllerComponents)
                              (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action.async {
    nutzerDao.all().map { case (nutzer) => Ok(views.html.index(nutzer)) }
  }

  def env() = Action { implicit request: Request[AnyContent] =>
    //    Ok("Nothing to see here")
    Ok(System.getenv("JDBC_DATABASE_URL"))
  }

  val produktForm = Form(
    mapping(
      "name" -> text(),
      "nachname" -> text(),
      "email" -> text(),
      "price" -> number())(Produkt.apply)(Produkt.unapply))

  def insertProdukt = Action.async { implicit request =>
    val produkt: Produkt = produktForm.bindFromRequest.get
    produktDao.insert(produkt).map(_ => Redirect(routes.HomeController.index))
  }

  val nutzerForm = Form(
    mapping(
      "id" -> optional(number),
      "vorname" -> text(),
      "nachname" -> text(),
      "strasse" -> text(),
      "plz" -> number(),
      "ort" -> text(),
      "kategorie" -> text(),
      "passwort" -> text())(Nutzer.apply)(Nutzer.unapply))

  def insertNutzer = Action.async { implicit request =>
    val nutzer: Nutzer = nutzerForm.bindFromRequest.get
    nutzerDao.insert(nutzer).map(_ => Redirect(routes.HomeController.index))
  }

}








