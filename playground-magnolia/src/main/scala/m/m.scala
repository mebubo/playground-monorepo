package m
import magnolia._
import language.experimental.macros

trait Show[A] {
  def show(a: A): String
}

object Show {
  implicit val showInt: Show[Int] = (a: Int) => a.toString
  implicit val showString: Show[String] = (a: String) => a

  implicit def showOption[A](implicit sa: Show[A]): Show[Option[A]] = new Show[Option[A]] {
    override def show(oa: Option[A]): String = oa.fold("Empty")(a => s"Some ${sa.show(a)}")
  }
  implicit class ShowOps[A](a: A)(implicit s: Show[A]) {
    def show: String = s.show(a)
  }

  type Typeclass[T] = Show[T]

  def combine[T](ctx: CaseClass[Show, T]): Show[T] = new Show[T] {
    def show(value: T): String = ctx.parameters.map { p: Param[Typeclass, T] =>
      s"${p.label}=${p.typeclass.show(p.dereference(value))}"
    }.mkString("-{", ",", "}-")
  }

  def dispatch[T](ctx: SealedTrait[Show, T]): Show[T] =
    new Show[T] {
      def show(value: T): String = ctx.dispatch(value) { sub: Subtype[Typeclass, T] =>
        sub.typeclass.show(sub.cast(value))
      }
    }

  implicit def gen[T]: Show[T] = macro Magnolia.gen[T]

}

case class A(a: Option[B])
case class B(b: (String, String, Option[Int]))

//case class A(a: String)


object Main {
  def main(args: Array[String]): Unit = {
    import Show.ShowOps
    val a: Option[Int] = Some(1)
    val b: Option[Int] = None
    val c = (a, b)
    println(a.show)
    println(b.show)
    println(c.show)
    val x: A = A(Some(B(("foo", "bar", Some(1)))))
//    val x: A = A("foo")
    println(Show.gen.show(x))
    println(x.show)
  }
}