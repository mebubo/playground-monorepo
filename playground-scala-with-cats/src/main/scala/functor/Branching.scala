package functor

import cats.Functor

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

object Tree {

  def branch[A](l: Tree[A], r: Tree[A]): Tree[A] = Branch(l, r)

  def leaf[A](v: A): Tree[A] = Leaf(v)

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
      case Leaf(v) => Leaf(f(v))
    }
  }

}

object M {

  def main(args: Array[String]): Unit = {
    import cats.syntax.functor._
    //    println(Branch(Leaf(10), Leaf(20)).map(_ * 2))
    println(Tree.branch(Leaf(10), Leaf(20)).map(_ * 2))
  }
}
