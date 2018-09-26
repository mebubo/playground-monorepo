package astronauts2

import shapeless.Generic

object Ch2 {

  case class IceCream(name: String, numCherries: Int, inCone: Boolean)

//  case class A(a: Option[B])
  case class A(a: B)
//  case class B(b: (String, String, Option[Int]))
  case class B(b: String)

  val iceCreamGen = Generic[IceCream]
  val aGen = Generic[A]

  val iceCream = IceCream("Sundae", 1, false)
//  val a: A = A(Some(B(("foo", "bar", Some(1)))))
    val a: A = A(B("foo"))

  def main(args: Array[String]): Unit = {
    println(iceCreamGen.to(iceCream))
    println(aGen.to(a))
    println(aGen)
  }

}
