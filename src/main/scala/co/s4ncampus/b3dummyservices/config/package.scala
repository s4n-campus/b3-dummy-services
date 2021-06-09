package co.s4ncampus.b3dummyservices

import io.circe.Decoder
import io.circe.generic.semiauto._

package object config {
  implicit val srDec: Decoder[ServerConfig] = deriveDecoder
  implicit val dbconnDec: Decoder[DatabaseConnectionsConfig] = deriveDecoder
  implicit val dbconfig: Decoder[DatabaseConfig] = deriveDecoder
  implicit val dbDec: Decoder[B3DummyServicesConfig] = deriveDecoder

}
