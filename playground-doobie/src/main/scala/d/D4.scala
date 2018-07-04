package d

object D4 {

  import doobie._
  import doobie.implicits._
  import cats._
  import cats.data._
  import cats.effect.IO
  import cats.implicits._

  val xa = Transactor.fromDriverManager[IO]("org.postgresql.Driver", "jdbc:postgresql:world", "me", "me")

  val y = xa.yolo

  import y._


  case class Country(code: String, name: String, pop: Int, gnp: Option[BigDecimal])

  def biggerThan(minPop: Int) =
    sql"""
    select code, name, population, gnp
    from country
    where population > $minPop
  """.query[Country]


  biggerThan(0).check.unsafeRunSync
  biggerThan(0).checkOutput.unsafeRunSync

  def main(args: Array[String]): Unit = {

  }

}

