package models

import play.api.libs.json.Json

case class Carro(placa: String, marca: String, modelo: Int)

object Carro{
  implicit val carroFormat = Json.format[Carro]
}
