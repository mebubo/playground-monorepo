object PatternMatching {
  def main(args: Array[String]): Unit = {
    val X = "Foo"
    // X: String = Foo

    val Y = "Bar"
    // Y: String = Bar

    val Z = "Baz"
    // Z: String = Baz

    val a = "Bar" match {
      case X => "It's foo!"
      case Y => "It's bar!"
      case Z => "It's baz!"
    }

    println(a)
  }
}

object Positive {
  def unapply(arg: Int): Option[Int] = if (arg > 0) Some(arg) else None

  def main(args: Array[String]): Unit = {
    0 match {
      case Positive(x) => println(x)
      case _ => println("not positive")
    }
    1 match {
      case Positive(x) => println(x)
      case _ => println("not positive")
    }
  }
}

object Titlecase {
  def unapply(arg: String): Option[String] = {
    val words = arg.split(" ").toList
    val result = words.map {
      case "" => ""
      case word => word.substring(0, 1).toUpperCase + word.substring(1)
    }.mkString(" ")
    Some(result)
  }

  def main(args: Array[String]): Unit = {
    println("aaa bbb ccc" match {
      case Titlecase(s) => s
    })
  }
}
