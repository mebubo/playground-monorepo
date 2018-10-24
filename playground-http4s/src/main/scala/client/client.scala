package client

import cats.effect._
import cats.implicits._
import org.http4s.Request
import org.http4s.client.Client
import org.http4s.dsl.io._
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.middleware.{Retry, RetryPolicy}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.global

object ClientExample extends IOApp {

  def print[A](a: A): IO[Unit] = IO(println(a))

  def google(client: Client[IO]): IO[Unit] = {
    val page: IO[String] = client.expect[String](uri("https://www.google.com/"))
    val p72 = page.map(_.take(72))

    val ps: List[IO[Unit]] = (for (_ <- 1 to 2)
      yield page.map(_.take(72)).flatMap(print)).toList

    ps.sequence[IO, Unit].as(Unit)
  }

  def retry(client: Client[IO]): IO[Either[Throwable, Unit]] = {
    val max = 4
    var attemptsCounter = 1
    val policy = RetryPolicy[IO] { attempts: Int =>
      if (attempts >= max) None
      else {
        attemptsCounter = attemptsCounter + 1
        println("+1")
        Some(1000.milliseconds)
      }
    }
    val retryClient = Retry[IO](policy)(client)
    val req = Request[IO](GET, uri("https://httpbin.org/status/408"))

    retryClient
      .fetch(req) { _ =>
        IO.unit
      }
      .attempt
  }

  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO](global).resource
      .use(retry)
      .as(ExitCode.Success)
}

