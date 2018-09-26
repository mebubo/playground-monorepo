package meet

import cats.{Eq, Show}
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._
import cats.syntax.eq._

final case class Cat(name: String, age: Int, color: String)

object CatInstances {
  implicit val catShow: Show[Cat] = Show.show { cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }

  implicit val catEq: Eq[Cat] = Eq.instance[Cat] { (c1, c2) =>
    (c1.name === c2.name) &&
      (c1.age === c2.age) &&
      (c1.color === c2.color)
  }
}

object MeetCats {



  def main(args: Array[String]): Unit = {
    val showInt: Show[Int] = Show.apply[Int]
    val showString: Show[String] = Show.apply[String]

    println(showInt.show(1))
    println(showString.show("foo"))

    val cat1 = Cat("Garfield", 38, "ginger and black")
    val cat2 = Cat("Heathcliff", 32, "orange and black")

    import CatInstances._
    println(cat1.show)

    println(cat1 === cat2)
    println(cat1 =!= cat2)

    val optionCat1 = Option(cat1)
    val optionCat2 = Option.empty

    import cats.instances.option._

    println(optionCat1 === optionCat2)
    println(optionCat1 =!= optionCat2)

    "foo" ++ "bar"
  }
}
