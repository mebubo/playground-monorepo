package merge

import shapeless._

object CaseClassMergeDemo {
  import mergeSyntax._

  case class Foo(i: Int, s: String, b: Boolean, foo: Foo2)
  case class Foo2(x: Int, y: String)
  case class Foo3(x: Int)
  case class Bar(b: Boolean, s: String, foo: Foo3)

  val foo = Foo(23, "foo", true, Foo2(3, "b"))
  val bar = Bar(false, "bar", Foo3(4))

//  val merged = foo merge bar
//  assert(merged == Foo(23, "bar", false))

  val fooj = new FooJ("fooj", "aaaaaaaa")
  val barj = new BarJ("bbb")

//  val mergedj = fooj merge barj

  def main(args: Array[String]): Unit = {
//    println(merged)
//    println(mergedj)
  }
}

// Implementation in terms of LabelledGeneric ...
object mergeSyntax {
  implicit class MergeSyntax[T](t: T) {
    def merge[U](u: U)(implicit merge: CaseClassMerge[T, U]): T = merge(t, u)
  }
}

trait CaseClassMerge[T, U] {
  def apply(t: T, u: U): T
}

object CaseClassMerge {
  import shapeless.ops.record.Merger

  def apply[T, U](implicit merge: CaseClassMerge[T, U]): CaseClassMerge[T, U] = merge

  implicit def mkCCMerge[T, U, RT <: HList, RU <: HList]
    (implicit
      tgen: LabelledGeneric.Aux[T, RT],
      ugen: LabelledGeneric.Aux[U, RU],
      merger: Merger.Aux[RT, RU, RT]
    ): CaseClassMerge[T, U] =
    new CaseClassMerge[T, U] {
      def apply(t: T, u: U): T =
        tgen.from(merger(tgen.to(t), ugen.to(u)))
    }

}