package merge

object AnyOps {
  implicit val pickNonNull: Merger[Nothing] =
    (a1: Nothing, a2: Nothing) => if (a1 != null) a1 else a2
}

trait Merger[A] {
  def merge(a1: A, a2: A): A
}

object Merger {
  import AnyOps._
  def merge[A](a1: A, a2: A)(implicit merger: Merger[A]): A = merger.merge(a1, a2)
}
