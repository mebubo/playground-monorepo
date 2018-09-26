sealed trait IntList
case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

object LazyList {
  def sum(l: IntList): Int = {
    @scala.annotation.tailrec
    def go(curr: Int, l: IntList): Int =
      l match {
        case End => curr
        case Pair(n, tail) => go(curr + n, tail)
      }
    go(0, l)
  }

  def main(args: Array[String]): Unit = {
    val s = sum(Pair(1, Pair(2, Pair(3, End))))
    println(s)
  }
}
