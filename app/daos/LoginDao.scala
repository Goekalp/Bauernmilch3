package daos

import model.Nutzer
import model.Login
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class LoginDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                        (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    import profile.api._

    private val Loggings = TableQuery[LoginTable]

    def all(): Future[Seq[Login]] = db.run(Loggings.result)

    def insert(login: Login): Future[Unit] = db.run(Loggings += login).map { _ => () }

    private class LoginTable(tag: Tag) extends Table[Login](tag, "nutzer") {


      def vorname = column[String]("vorname")
      def nachname = column[String]("nachname")
      def passwort = column[String]("passwort")

      def * = (vorname, nachname,passwort) <> (Login.tupled, Login.unapply)

    }

}