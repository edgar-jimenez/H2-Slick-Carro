package persistence

import javax.inject.{Inject, Singleton}
import models.Carro
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class CarroRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._

  private val carros = TableQuery[CarroTable]

  def all() : Future[Seq[Carro]] = db.run(carros.result)

  def add(carro: Carro): Future[Unit] = db.run(carros += carro).map(_ => ())

  private class CarroTable(tag: Tag) extends Table[Carro] (tag, "CARRO"){
    def placa = column[String]("PLACA", O.PrimaryKey)
    def marca = column[String]("MARCA")
    def modelo = column[Int]("MODELO")

    def * = (placa, marca, modelo) <> ( (Carro.apply _).tupled, Carro.unapply)
  }
}
