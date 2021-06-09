package co.s4ncampus.b3dummyservices.domain

import cats.Monad
import cats.implicits._
import io.nayuki.qrcodegen.QrCode

import java.io.ByteArrayOutputStream
import java.util.Base64
import javax.imageio.ImageIO

class QrCodeService [F[_]]{
  def generate(id: String)(implicit M: Monad[F]): F[String] = {
    val qr = QrCode.encodeText(id, QrCode.Ecc.MEDIUM)
    val os: ByteArrayOutputStream = new ByteArrayOutputStream()
    ImageIO.write(qr.toImage(4, 10), "png", os)
    Base64.getEncoder.encodeToString(os.toByteArray).pure[F]
  }
}

object QrCodeService {
  def apply[F[_]]: QrCodeService[F] = new QrCodeService[F]
}