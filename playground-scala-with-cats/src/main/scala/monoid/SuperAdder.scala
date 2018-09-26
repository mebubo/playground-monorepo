package monoid

import cats.Monoid
import cats.instances.int._
import cats.instances.option._

case class Order(totalCost: Double, quantity: Double)

object Order {
  implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
    override def empty: Order = Order(0, 0)

    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
  }
}

object SuperAdder {

  def add[A](as: List[A])(implicit m: Monoid[A]): A =
    as.foldLeft(m.empty)(m.combine)

  def main(args: Array[String]): Unit = {
    println(add(List(1, 2, 3)))
    println(add(List(Some(1), None, Some(2))))
//    println(add(List(Some(1), Some(3), Some(2))))
    println(add(List(Order(1, 3), Order(0, 5.6))))
  }
}
