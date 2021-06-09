package co.s4ncampus.b3dummyservices.domain

import cats.Monad
import cats.implicits._

import java.util.UUID

class BankServices [F[_]]{
  def pay(pr: PaymentRequest)(implicit M: Monad[F]): F[PaymentResult] = {
    println("pr="+pr)
    PaymentResult(UUID.randomUUID().toString).pure[F]
  }
}

object BankServices {
  def apply[F[_]](): BankServices[F] = new BankServices[F]
}