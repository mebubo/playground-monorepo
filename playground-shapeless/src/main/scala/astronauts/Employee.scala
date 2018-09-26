package astronauts

case class Employee(name: String, number: Int, manager: Boolean)

case class IceCream(name: String, numCherries: Int, inCone: Boolean)

sealed trait Shape
final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

sealed trait Tree[A]
final case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

import shapeless.Generic.Aux
import shapeless._

object G {

  type X = String :: Int :: Boolean :: HNil

  val genericEmployee: X = Generic[Employee].to(Employee("Dave", 123, manager = false))
  val genericIceCream: X = Generic[IceCream].to(IceCream("Sundae", 1, inCone = false))

  val iceCreamGen: Aux[IceCream, X] = Generic[IceCream]

  val cream: IceCream = iceCreamGen.from("foo" :: 10 :: false :: HNil)

  def genericCsv(gen: X): List[String] = List(gen.head, gen(1).toString, gen(2).toString)

  val newProduct: Int :: X = 42 :: genericEmployee

  case class Red()
  case class Amber()
  case class Green()

  type Light = Red :+: Amber :+: Green :+: CNil

  val red: Light = Inl(Red())
  val green: Light = Inr(Inr(Inl(Green())))

  type S = Circle :+: Rectangle :+: CNil
  val gen: Aux[Shape, S] = Generic[Shape]
  val rect: S = gen.to(Rectangle(1, 1))
  val circ: S = gen.to(Circle(1))

  def main(args: Array[String]): Unit = {
    println(genericCsv(genericEmployee))
    println(genericCsv(genericIceCream))
    println(newProduct)
    println(cream)
    println(rect)
    println(circ)
  }
}

