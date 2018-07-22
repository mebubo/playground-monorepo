package h

import cats.effect._, org.http4s._, org.http4s.dsl.io._, scala.concurrent.ExecutionContext.Implicits.global

object h {
  val helloWorldService: HttpService[IO] = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }
}
