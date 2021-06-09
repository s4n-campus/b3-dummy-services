package co.s4ncampus.b3dummyservices.controller

import cats.effect.Sync
import cats.syntax.all._
import co.s4ncampus.b3dummyservices.domain.QrCodeService
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes

class QrCodeController[F[_]: Sync] extends Http4sDsl[F] {

  private def generate(qrCodeService: QrCodeService[F]): HttpRoutes[F] =
    HttpRoutes.of[F] {
      case GET -> Root / id  =>
        for {
          base64Qr <- qrCodeService.generate(id)
          resp <- Ok(base64Qr)
        } yield resp
    }

  def endpoints(qrCodeService: QrCodeService[F]): HttpRoutes[F] = {
    generate(qrCodeService)
  }

}

object QrCodeController {
  def endpoints[F[_]: Sync](qrCodeService: QrCodeService[F]): HttpRoutes[F] =
    new QrCodeController[F].endpoints(qrCodeService)
}
