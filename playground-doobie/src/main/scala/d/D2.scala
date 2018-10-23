package d

import cats.effect.IO
import doobie._
import doobie.implicits._
import fs2.Stream

import scala.concurrent.ExecutionContext

object D2 {

  implicit val cs = IO.contextShift(ExecutionContext.global)

  case class Country(code: String, name: String, pop: Int, gnp: Option[Double])
  case class Code(code: String)
  case class Country2(name: String, pop: Int, gnp: Option[Double])

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", "jdbc:postgresql:world", "me", "me"
  )

  def main(args: Array[String]): Unit = {
    val q = sql"select name from country"
      .query[String] // Query0[String]
      .to[List] // ConnectionIO[List[String]]
      .transact(xa) // IO[List[String]]
      .unsafeRunSync // List[String]
      .take(5) // List[String]
      .foreach(println) // Unit

    val q2 = sql"select name from country"
      .query[String] // Query0[String]
      .stream // Stream[ConnectionIO, String]
      .take(5) // Stream[ConnectionIO, String]
      .compile.toList // ConnectionIO[List[String]]
      .transact(xa) // IO[List[String]]
      .unsafeRunSync // List[String]
      .foreach(println) // Unit

    val qStreaming: Stream[IO, Country] = sql"select name, population, gnp from country"
      .query[Country] // Query0[Country]
      .stream // Stream[ConnectionIO, Country]
      .transact(xa) // Stream[IO, Country]

    val y = xa.yolo

    import y._

    val q3 = sql"select name from country"
      .query[String] // Query0[String]
      .stream // Stream[ConnectionIO, String]
      .take(5) // Stream[ConnectionIO, String]
      .quick // IO[Unit]
      .unsafeRunSync

    val q4 = sql"select code, name, population, gnp from country"
      .query[(String, String, Int, Option[Double])]
      .stream
      .take(5)
      .quick
      .unsafeRunSync

    import shapeless._

    val q5 =
      sql"select code, name, population, gnp from country"
        .query[String :: String :: Int :: Option[Double] :: HNil]
        .stream
        .take(5)
        .quick
        .unsafeRunSync

    import shapeless.record.Record

    type Rec = Record.`'code -> String, 'name -> String, 'pop -> Int, 'gnp -> Option[Double]`.T

    val q6 = sql"select code, name, population, gnp from country"
      .query[Rec]
      .stream
      .take(5)
      .quick
      .unsafeRunSync


    val q7 = sql"select code, name, population, gnp from country"
      .query[Country]
      .stream
      .take(5)
      .quick
      .unsafeRunSync


    val q8 = sql"select code, name, population, gnp from country"
      .query[(Code, Country2)]
      .stream.take(5)
      .compile.toList
      .map(_.toMap)
      .quick
      .unsafeRunSync

  }

}
