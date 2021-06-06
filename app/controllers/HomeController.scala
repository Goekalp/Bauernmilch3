package controllers

import play.api.mvc._

import javax.inject.{Inject, _}
import daos.{BauerDAO, LoginDao, NutzerDao, ProduktDao}
import model.{Bauer, Login, Nutzer, Produkt}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, optional, text}

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(produktDao: ProduktDao, bauerDao: BauerDAO, loginDao: LoginDao, nutzerDao: NutzerDao, controllerComponents: ControllerComponents)
                              (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index() = Action {
    Ok(views.html.index())
  }


  def nutzer() = Action.async {
    nutzerDao.all().map { case (nutzer) => Ok(views.html.Nutzer(nutzer)) }
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

  //  def insertProdukt = Action.async { implicit request =>
  //    val produkt: Produkt = produktForm.bindFromRequest.get
  //    produktDao.insert(produkt).map(_ => Redirect(routes.HomeController.index))
  //  }

  val nutzerForm = Form(
    mapping(
      "id" -> optional(number),
      "vorname" -> text(),
      "nachname" -> text(),
      "strasse" -> text(),
      "plz" -> number(),
      "ort" -> text(),
      "bauername" -> text(),
      "anzahlflasche" -> number())(Nutzer.apply)(Nutzer.unapply))

  def insertNutzer = Action.async { implicit request =>
    val nutzer: Nutzer = nutzerForm.bindFromRequest.get
    nutzerDao.insert(nutzer).map(_ => Redirect(routes.HomeController.nutzer()))
  }

  val bauerForm = Form(
    mapping(
      "id" -> optional(number),
      "vorname" -> text(),
      "nachname" -> text(),
      "firmenname" -> text(),
      "anzahlflasche" -> number())(Bauer.apply)(Bauer.unapply))

  def insertBauer = Action.async { implicit request =>
    val bauer: Bauer = bauerForm.bindFromRequest.get
    bauerDao.insert(bauer).map(_ => Redirect(routes.HomeController.bauer))
  }

  def bauer() = Action.async {
    bauerDao.all().map { case (bauer) => Ok(views.html.Bauer(bauer)) }
  }


  def login() = Action.async { implicit request =>
    val loggings: Login = loginForm.bindFromRequest.get
    loginDao.all().map { case (login) =>
      var isValid = false
      login.map { log =>
        if (loggings.vorname.equals(log.vorname) && loggings.nachname.equals(log.nachname) && loggings.passwort.equals(log.passwort)) isValid = true
      }
      if (isValid) Redirect(routes.HomeController.bauer)
      else NotFound
    }
  }

  val loginForm = Form(
    mapping(
      "vorname" -> text(),
      "nachname" -> text(),
      "passwort" -> text())(Login.apply)(Login.unapply))

  def signup() = Action.async {
    nutzerDao.all().map { case (users) => Ok(views.html.Signin()) }

  }
}








