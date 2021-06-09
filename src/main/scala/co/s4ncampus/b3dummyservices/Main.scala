package co.s4ncampus.b3dummyservices


import co.s4ncampus.b3dummyservices.config.B3DummyServicesConfig
import co.s4ncampus.b3dummyservices.controller.{BankController, QrCodeController}
import co.s4ncampus.b3dummyservices.domain.{BankServices, QrCodeService}
import cats.effect.IOApp
import cats.effect.{ExitCode, IO}
import cats.effect.ContextShift
import cats.effect.ConcurrentEffect
import cats.effect.Timer
import cats.effect.Resource
import org.http4s.server.Server
import io.circe.config.parser
import org.http4s.server.blaze.BlazeServerBuilder
//import cats.effect.Blocker
import doobie.util.ExecutionContexts
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router


object Main extends IOApp{

    private def server[F[_]: ContextShift: ConcurrentEffect: Timer]: Resource[F, Server[F]] = 
        for {
            conf <- Resource.liftF(parser.decodePathF[F, B3DummyServicesConfig]("b3dummyservices"))
            serverExecutionContexts <- ExecutionContexts.cachedThreadPool[F]
            //connectionExecutionContext <- ExecutionContexts.fixedThreadPool[F](conf.db.connections.poolSize)
            //transactionsExecutionContext <- ExecutionContexts.cachedThreadPool[F]
            //xa <- DatabaseConfig.dbTransactor(conf.db, connectionExecutionContext, Blocker.liftExecutionContext(transactionsExecutionContext))
            bs = BankServices[F]
            qrs = QrCodeService[F]
            httpApp = Router(
                "/fake-bank" -> BankController.endpoints[F](bs),
                "/qr-generator" -> QrCodeController.endpoints[F](qrs)
            ).orNotFound
            //_ <- Resource.liftF(DatabaseConfig.initializeDb(conf.db))
            server <- BlazeServerBuilder[F](serverExecutionContexts)
                .bindHttp(conf.server.port, conf.server.host)
                .withHttpApp(httpApp)
                .resource
        } yield server

    def run(args: List[String]): IO[ExitCode] = server.use(_ => IO.never).as(ExitCode.Success)
}
