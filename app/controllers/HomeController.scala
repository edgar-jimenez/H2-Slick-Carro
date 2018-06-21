package controllers

import javax.inject._
import models.{ Carro}
import persistence.{ CarroRepository}
import play.api.libs.json.{Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class HomeController @Inject()(cc: ControllerComponents, carroRepository: CarroRepository)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  populateDate()

  def listarCarros() = Action.async{ implicit request: Request[AnyContent] =>
    val fCarros: Future[Seq[Carro]] = carroRepository.all()

    fCarros.map(s => Ok( Json.toJson(s)))
  }

  def addCarro() = Action.async(parse.json[Carro]) { request =>
    insertCarro(request.body)
  }

  private def insertCarro(carro: Carro): Future[Result] = {
    carroRepository.add(carro)
      .map(_ => Ok("Carro Agregada Con Exito"))
      .recoverWith{
        case error: Exception => {
          error.printStackTrace(System.err)
          Future.successful( InternalServerError(s"no se pudo agragar al Carro: $carro"))
        }
      }
  }

  private def populateDate() {
    insertCarro(new Carro("CKT123", "BMW", 2015))
    insertCarro(new Carro("RMP256", "AUDI", 2018))
    insertCarro(new Carro("JMK533", "NISSAN", 2010))
  }

}
