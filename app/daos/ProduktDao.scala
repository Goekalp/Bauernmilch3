package daos

import model.Produkt
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class ProduktDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                          (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Produkte = TableQuery[ProduktTable]

  def all(): Future[Seq[Produkt]] = db.run(Produkte.result)

  def insert(produkt: Produkt): Future[Unit] = db.run(Produkte += produkt).map { _ => () }

  private class ProduktTable(tag: Tag) extends Table[Produkt](tag, "PRODUKT") {

    def name = column[String]("NAME", O.PrimaryKey)
    def nachname = column[String]("NACHNAME")
    def email = column[String]("EMAIL")
    def color = column[Int]("PRICE")

    def * = (name, nachname, email, color) <> (Produkt.tupled, Produkt.unapply)

  }
}
