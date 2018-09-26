sealed trait Maybe[+A] {
  def fold[B](empty: B)(full: A => B): B = this match {
    case Empty => empty
    case Full(a) => full(a)
  }
}

final case class Full[A](a: A) extends Maybe[A]
case object Empty extends Maybe[Nothing]

object Ffsad {
  def main(args: Array[String]): Unit = {
    val x: Maybe[Int] = Empty
  }
}