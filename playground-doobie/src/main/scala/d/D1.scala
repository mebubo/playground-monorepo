package d

import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import doobie.util.transactor.Transactor.Aux
import scala.concurrent.ExecutionContext

object D1 {

  implicit val cs = IO.contextShift(ExecutionContext.global)

  val program1: ConnectionIO[Int] = 42.pure[ConnectionIO]

  val program2: ConnectionIO[Int] = sql"select 42".query[Int].unique

  val program3: ConnectionIO[(Int, Double)] = for {
    a <- sql"select 42".query[Int].unique
    b <- sql"select random()".query[Double].unique
  } yield (a, b)

  val program3a: ConnectionIO[(Int, Double)] = {
    val a = sql"select 42".query[Int].unique
    val b = sql"select random()".query[Double].unique
    (a, b).tupled
  }

  val program4: ConnectionIO[List[(Int, Double)]] = program3a.replicateA(5)

  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.sqlite.JDBC",
    "jdbc:sqlite:./sqlite.db",
    "",
    ""
  )

  def main(args: Array[String]): Unit = {
    val io1: IO[Int] = program1.transact(xa)
    val io2: IO[Int] = program2.transact(xa)
    val io3: IO[(Int, Double)] = program3.transact(xa)
    val io3a: IO[(Int, Double)] = program3a.transact(xa)
    val io4 = program4.transact(xa)
    val v1: Int = io1.unsafeRunSync
    val v2: Int = io2.unsafeRunSync
    val v3: (Int, Double) = io3.unsafeRunSync
    val v3a: (Int, Double) = io3a.unsafeRunSync
    val v4: List[(Int, Double)] = io4.unsafeRunSync
    println(v1)
    println(v2)
    println(v3)
    println(v3a)
    println(v4)
  }
}
