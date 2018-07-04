package d

import doobie._
import doobie.implicits._
import cats._
import cats.data._
import cats.effect.IO
import cats.implicits._

object D3 {
  val xa = Transactor.fromDriverManager[IO]("org.postgresql.Driver", "jdbc:postgresql:world", "me", "me")
  val y = xa.yolo

  import y._

  case class Country(code: String, name: String, pop: Int, gnp: Option[Double])

  def biggerThan(minPop: Int): Query0[Country] = sql"""
    select code, name, population, gnp
    from country
    where population > $minPop
  """.query[Country]

  def populationIn(range: Range) = sql"""
        select code, name, population, gnp
        from country
        where population > ${range.min}
        and   population < ${range.max}
      """.query[Country]

  def populationIn2(range: Range, codes: NonEmptyList[String]) = {
    val q =
      fr"""
    select code, name, population, gnp
    from country
    where population > ${range.min}
    and   population < ${range.max}
    and   """ ++ Fragments.in(fr"code", codes) // code IN (...)
    q.query[Country]
  }

  import fs2.Stream

  val q =
    """
  select code, name, population, gnp
  from country
  where population > ?
  and   population < ?
  """

  def proc(range: Range): Stream[ConnectionIO, Country] =
    HC.stream[Country](q, HPS.set((range.min, range.max)), 512)


  def main(args: Array[String]): Unit = {
    biggerThan(120000000).quick.unsafeRunSync
    println
    populationIn(30000000 to 40000000).quick.unsafeRunSync
    println
    populationIn2(100000000 to 300000000, NonEmptyList.of("USA", "BRA", "PAK", "GBR")).quick.unsafeRunSync
    println
    proc(150000000 to 200000000).quick.unsafeRunSync
  }
}
