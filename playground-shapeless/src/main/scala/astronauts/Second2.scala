package astronauts

import shapeless.{::, Generic, HList, HNil}

trait Second2[L <: HList] {
  type Out
  def apply(in: L): Out
}

object Second2 {
  def apply[L <: HList](implicit inst: Second2[L]): Second2[L] = inst

  implicit def hListSecond2[A, B, Rest <: HList]: Second2[A :: B :: Rest] = new Second2[A :: B :: Rest] {
    override type Out = B
    override def apply(in: A :: B :: Rest): Out = in.tail.head
  }


//  def secondField0[A](a: A)(implicit gen: Generic[A], sec: Second2[gen.Repr]): sec.Out =
//    sec(gen.to(a))

  def secondField[A, R <: HList](a: A)(implicit gen: Generic.Aux[A, R], sec: Second[R]): sec.Out =
    sec(gen.to(a))
}

object Second2Main {
  def main(args: Array[String]): Unit = {
    val s = Second2[Int :: String :: HNil]

    println(s(1 :: "foo" :: HNil))
//    println(s(1 :: 2 :: HNil))

    val sf = Second2.secondField(Employee("name", 1, false))

    println(sf)

  }
}
