package daos

import model.Bauer
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


  class BauerDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    import profile.api._

    private val Bauern = TableQuery[BauerTable]

    def all(): Future[Seq[Bauer]] = db.run(Bauern.result)

    def insert(bauer: Bauer): Future[Unit] = db.run(Bauern += bauer).map { _ => () }

    private class BauerTable(tag: Tag) extends Table[Bauer](tag, "bauer") {

      def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
      def vorname = column[String]("vorname")
      def nachname = column[String]("nachname")
      def firmennamen = column[String]("firmenname")
      def anzahlflasche = column[Int]("anzahlflasche")

      def * = (id.?, vorname, nachname, firmennamen, anzahlflasche) <> (Bauer.tupled, Bauer.unapply)
    }
}
