package functionality

import simulacrum.typeclass
import simulacrum.op

@typeclass trait Ordering[T] {
  def compare(x: T, y: T): Int
  @op("<") def lt(x: T, y: T): Boolean = compare(x, y) < 0
  @op(">") def gt(x: T, y: T): Boolean = compare(x, y) > 0
}

@typeclass trait Numeric[T] extends Ordering[T] {
  @op("+") def plus(x: T, y: T): T
  @op("*") def times(x: T, y: T): T
  @op("unary_-") def negate(x: T): T
  def zero: T
  def abs(x: T): T = if (lt(x, zero)) negate(x) else x
}

object Num {

  import Numeric.ops._

  def signOfTheTimes[T: Numeric] (t: T): T = - (t.abs) * t

  implicit val NumericDouble: Numeric[Double] = new Numeric[Double] {
    def plus(x: Double, y: Double): Double = x + y
    def times(x: Double, y: Double): Double = x * y
    def negate(x: Double): Double = -x
    def zero: Double = 0.0
    def compare(x: Double, y: Double): Int = java.lang.Double.compare(x, y)

    override def lt(x: Double, y: Double): Boolean = x < y
    override def gt(x: Double, y: Double): Boolean = x > y
    override def abs(x: Double): Double = java.lang.Math.abs(x)
  }

  def main(args: Array[String]): Unit = {
    println(signOfTheTimes(10.0))
    println(signOfTheTimes(-10.0))
  }

}
