import scala.concurrent.Future

trait Terminal[C[_]] {
  def read: C[String]
  def write(t: String): C[Unit]
}

object Types2 {
  type Now[X] = X
}

import Types2._

object TerminalSync extends Terminal[Now] {
  override def read: String = ???
  override def write(t: String): Unit = ???
}

object TerminalAsync extends Terminal[Future] {
  override def read: Future[String] = ???
  override def write(t: String): Future[Unit] = ???
}

trait Monad[C[_]] {
  def doAndThen[A, B](c: C[A])(f: A => C[B]): C[B]
  def create[B](b: B): C[B]
}

object Monad {
  implicit class Ops[A, C[_]](c: C[A]) {
    def flatMap[B](f: A => C[B])(implicit e: Monad[C]): C[B] =
      e.doAndThen(c)(f)
    def map[B](f: A => B)(implicit e: Monad[C]): C[B] =
      e.doAndThen(c)(f andThen e.create)
  }
}

import Monad._

object TerminalOps {
  def echo[C[_]](t: Terminal[C], e: Monad[C]): C[String] = {
    e.doAndThen(t.read) { in: String =>
      e.doAndThen(t.write(in)) { _: Unit =>
        e.create(in)
      }
    }
  }
  def echoImplicit[C[_]](implicit t: Terminal[C], e: Monad[C]): C[String] = {
    t.read.flatMap { in: String =>
      t.write(in).map { _: Unit =>
        in
      }
    }
  }
  def echoFor[C[_]](implicit t: Terminal[C], e: Monad[C]): C[String] =
    for {
      in <- t.read
      _ <- t.write(in)
    } yield in
}

