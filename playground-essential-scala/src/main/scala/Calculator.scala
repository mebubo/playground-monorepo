sealed trait Calculation

final case class Success(result: Int) extends Calculation

final case class Failure(reason: String) extends Calculation

object Calculator {
  def +(calc: Calculation, n: Int): Calculation =
    calc match {
      case Success(m) => Success(n + m)
      case fail => fail
    }
  def -(calc: Calculation, n: Int): Calculation =
    calc match {
      case Success(m) => Success(n - m)
      case fail => fail
    }
  def /(calc: Calculation, n: Int): Calculation =
    calc match {
      case Success(m) if n != 0 => Success(m/n)
      case Success(m) => Failure("zero division")
      case fail => fail
    }
}
