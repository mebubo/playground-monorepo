case class Case(n: Int, b: String)
case object A {}

object Case {
  def main(args: Array[String]): Unit = {
    val c = Case(1, "a")
    println(c.productArity)
  }
}

