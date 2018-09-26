trait Equal[A] {
  def equal(a1: A, a2: A): Boolean
}

object Equal {
  def apply[A](implicit eq: Equal[A]): Equal[A] = eq
}

object Eq {
  def apply[A](x: A, y: A)(implicit eq: Equal[A]): Boolean = eq.equal(x, y)

  implicit class EqOps[A](a: A) {
    def ===(o: A)(implicit eq: Equal[A]): Boolean = eq.equal(a, o)
  }
}

case class Person(name: String, email: String)

object Person {
  implicit object PersonWriter extends HtmlWriter[Person] {
    def write(p: Person) = s"<span>${p.name}</span>"
  }
}

object EmailEqualImplicit {
  implicit object EmailEqual extends Equal[Person] {
    override def equal(a1: Person, a2: Person): Boolean = a1.email == a2.email
  }
}

object NameEmailEqualImplicit {
  implicit object NameEmailEqual extends Equal[Person] {
    override def equal(a1: Person, a2: Person): Boolean = a1.email == a2.email && a1.name == a2.name
  }
}

object StringEqualImplicit {
  implicit object StringEqual extends Equal[String] {
    override def equal(a1: String, a2: String): Boolean = a1 == a2
  }
}

object Examples {
  def byNameAndEmail = {
    import NameEmailEqualImplicit._
    Eq(Person("a", "b"), Person("c", "b"))
  }

  def byEmail = {
    import EmailEqualImplicit._
    Eq(Person("a", "b"), Person("c", "b"))
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    println(Examples.byEmail)
    println(Examples.byNameAndEmail)

    import Eq._
    import StringEqualImplicit._
    println("foo" === "bar")
    println("foo" === "foo")
  }
}
