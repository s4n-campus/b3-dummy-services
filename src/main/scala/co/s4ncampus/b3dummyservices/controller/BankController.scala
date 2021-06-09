package co.s4ncampus.b3dummyservices.controller

import co.s4ncampus.b3dummyservices.domain._
import cats.effect.Sync
import cats.syntax.all._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, HttpRoutes}

class BankController[F[_]: Sync] extends Http4sDsl[F] {

  implicit val paymentRequestDecoder: EntityDecoder[F, PaymentRequest] = jsonOf

  private def pay(bs: BankServices[F]): HttpRoutes[F] =
    HttpRoutes.of[F] {
      case req @ POST -> Root  =>
        for {
          pr <- req.as[PaymentRequest]
          creditResult <- bs.pay(pr)
          resp <- Ok(creditResult.asJson)
        } yield resp
    }

  def endpoints(bankService: BankServices[F]): HttpRoutes[F] = {
    pay(bankService)
  }

}

object BankController {
  def endpoints[F[_]: Sync](bs: BankServices[F]): HttpRoutes[F] =
    new BankController[F].endpoints(bs)
}
