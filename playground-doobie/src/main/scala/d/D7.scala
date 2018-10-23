package d

import scala.concurrent.ExecutionContext

object D7 {

  import doobie._
  import doobie.implicits._
  import cats._
  import cats.data._
  import cats.effect.IO
  import cats.implicits._

  implicit val cs = IO.contextShift(ExecutionContext.global)

  val xa = Transactor.fromDriverManager[IO]("org.postgresql.Driver", "jdbc:postgresql:world", "me", "me")

  val y = xa.yolo

  import y._

  val drop: ConnectionIO[Int] =
    sql"""
    DROP TABLE IF EXISTS person
  """.update.run

  val create: ConnectionIO[Int] =
    sql"""
    CREATE TABLE person (
      id   SERIAL,
      name VARCHAR NOT NULL UNIQUE,
      age  SMALLINT
    )
  """.update.run

  case class Person(id: Long, name: String, age: Option[Short])

  def insert1(name: String, age: Option[Short]): Update0 =
    sql"insert into person (name, age) values ($name, $age)".update

  def insert2(name: String, age: Option[Short]): ConnectionIO[Person] =
    for {
      _ <- sql"insert into person (name, age) values ($name, $age)".update.run
      id <- sql"select lastval()".query[Long].unique
      p <- sql"select id, name, age from person where id = $id".query[Person].unique
    } yield p

  def insert3(name: String, age: Option[Short]): ConnectionIO[Person] = {
    sql"insert into person (name, age) values ($name, $age)"
      .update
      .withUniqueGeneratedKeys("id", "name", "age")
  }

  type PersonInfo = (String, Option[Short])

  def insertMany(ps: List[PersonInfo]): ConnectionIO[Int] = {
    val sql = "insert into person (name, age) values (?, ?)"
    Update[PersonInfo](sql).updateMany(ps)
  }

  // Some rows to insert
  val data = List[PersonInfo](
    ("Frank", Some(12)),
    ("Daddy", None))

  def main(args: Array[String]): Unit = (for {
    x <- (drop, create).mapN(_ + _).transact(xa)
    _ <- IO(println(s"x: $x"))
    _ <- insert1("Alice", Some(12)).run.transact(xa)
    _ <- insert1("Bob", None).quick
    _ <- sql"select id, name, age from person".query[Person].quick
    _ <- insert2("Jimmy", Some(42)).quick
    _ <- insert3("Elvis", None).quick
    _ <- insertMany(data).quick
  } yield ()).unsafeRunSync

}
