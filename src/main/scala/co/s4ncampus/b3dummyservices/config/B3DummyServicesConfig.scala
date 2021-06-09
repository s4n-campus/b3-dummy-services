package co.s4ncampus.b3dummyservices.config

final case class ServerConfig(host: String, port: Int)
final case class B3DummyServicesConfig(db: DatabaseConfig, server: ServerConfig)