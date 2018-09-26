package intro

trait Printable[A] { self =>
  def format(a: A): String

  def contramap[B](f: B => A): Printable[B] = new Printable[B] {
    override def format(b: B): String = self.format(f(b))
  }
}

object PrintableInstances {
  implicit val printableString: Printable[String] = (a: String) => a
  implicit val printableInt: Printable[Int] = (a: Int) => a.toString
}

object Printable {
  def format[A](a: A)(implicit p: Printable[A]): String =
    p.format(a)

  def print[A](a: A)(implicit p: Printable[A]): Unit =
    println(format(a))
}

final case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit def printableCat: Printable[Cat] = (a: Cat) => {
    import PrintableInstances._
    val name = Printable.format(a.name)
    val age = Printable.format(a.age)
    val color = Printable.format(a.color)
    s"$name is a $age year-old $color cat."
  }
}

object PrintableSyntax {
  implicit class PrintableOps[A : Printable](a: A) {
    def format: String = Printable.format(a)
    def print(): Unit = Printable.print(a)
  }
}

final case class Box[A](value: A)

object Box {
  implicit def printableBox[A](implicit pa: Printable[A]): Printable[Box[A]] =
    pa.contramap(b => b.value)
}

object P {
  def main(args: Array[String]): Unit = {
    import PrintableInstances._
    Printable.print(1)
    Printable.print("foo")

    val cat = Cat("Garfield", 38, "ginger and black")
    Printable.print(cat)
    import PrintableSyntax.PrintableOps
    cat.print()

    Printable.print(Box("hello"))
    Printable.print(Box(2))
  }
}
