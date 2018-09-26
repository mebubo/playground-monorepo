object IntImplicits {
  class IntOps(n: Int) {
    val range: Seq[Int] = 0 until n
    def yeah: Unit = times { _ => println("Oh yeah!") }
    def times(f: Int => Unit): Unit = range foreach f
  }
  implicit def intOps(n: Int): IntOps = new IntOps(n)
}

object OhYeah {
  import IntImplicits._
  def main(args: Array[String]): Unit = {
    2.yeah
    -1.yeah
  }
}
