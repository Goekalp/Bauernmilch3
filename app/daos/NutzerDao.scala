package daos

import model.Nutzer
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


  class NutzerDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    import profile.api._

    private val Nutzers = TableQuery[NutzerTable]

    def all(): Future[Seq[Nutzer]] = db.run(Nutzers.result)

    def insert(nutzer: Nutzer): Future[Unit] = db.run(Nutzers += nutzer).map { _ => () }

    private class NutzerTable(tag: Tag) extends Table[Nutzer](tag, "nutzer") {

      def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
      def vorname = column[String]("vorname")
      def nachname = column[String]("nachname")
      def strasse = column[String]("strasse")
      def plz = column[Int]("plz")
      def ort = column[String]("ort")
      def bauername = column[String]("bauername")
      def anzahlflasche = column[Int]("anzahlflasche")

      def * = (id.?, vorname, nachname, strasse, plz, ort, bauername, anzahlflasche) <> (Nutzer.tupled, Nutzer.unapply)

    }

}
