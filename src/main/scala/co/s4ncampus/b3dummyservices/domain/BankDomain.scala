package co.s4ncampus.b3dummyservices.domain

final case class ExpireDate(year: String, month: String)
final case class CreditCard(number: String, cvc: String, expiresDate: ExpireDate)
final case class PaymentRequest(creditCard: CreditCard, amount: Double)
final case class PaymentResult(id: String)
