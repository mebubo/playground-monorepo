sealed trait Sum[+A, +B] {
  def fold[C](left: A => C)(right: B => C): C = this match {
    case Left(a) => left(a)
    case Right(b) => right(b)
  }

  def flatMap[AA >: A, C](f: B => Sum[AA, C]): Sum[AA, C] = this match {
    case Right(b) => f(b)
    case Left(a) => Left[A](a)
  }
}

final case class Left[A](value: A) extends Sum[A, Nothing]
final case class Right[B](value: B) extends Sum[Nothing, B]
