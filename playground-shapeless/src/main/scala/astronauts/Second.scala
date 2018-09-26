package astronauts

import shapeless.{::, HList, HNil}

trait Second[L <: HList] {
  type Out
  def apply(value: L): Out
}

object Second {
  type Aux[L <: HList, O] = Second[L] { type Out = O }

  //  def apply[L <: HList](implicit inst: Second[L]): Second[L] = inst
  def apply[L <: HList](implicit inst: Second[L]): Aux[L, inst.Out] = inst

//  implicit def hListSecond[A, B, Rest <: HList]: Second[A :: B :: Rest] =
  implicit def hListSecond[A, B, Rest <: HList]: Aux[A :: B :: Rest, B] =
    new Second[A :: B :: Rest] {
      type Out = B
      def apply(value: A :: B :: Rest): B = value.tail.head
    }

}

object SecondMain {
  def main(args: Array[String]): Unit = {
    println("hello")
    val s = Second[String :: String :: HNil]
    println(s("aaa" :: "bbb" :: HNil))
    // println(s("aaa" :: 1 :: HNil))
  }
}
