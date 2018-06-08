package intro

import intro.Types.{EitherString, Id}

import scala.language.higherKinds

trait Foo[C[_], A] {
  def create(i: A): C[A]
}

object FooList extends Foo[List, Int] {
  def create(i: Int): List[Int] = List(i)
}

object Types {
  type EitherString[T] = Either[String, T]
  type Id[T] = T
}

object FooEitherString extends Foo[EitherString, Int] {
  def create(i: Int): Either[String, Int] = Right(i)
}

object FooEitherString2 extends Foo[Either[String, ?], Int] {
  def create(i: Int): Either[String, Int] = Right(i)
}

object FooId extends Foo[Id, Int] {
  def create(i: Int): Int = i
}