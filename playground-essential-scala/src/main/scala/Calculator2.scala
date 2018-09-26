sealed trait Expression

final case class Number(n: Double) extends Expression
final case class Addition(left: Expression, right: Expression) extends Expression
final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Division(left: Expression, right: Expression) extends Expression
final case class Sqrt(ex: Expression) extends Expression

sealed trait Calculation2

final case class Success2(n: Double) extends Calculation2
final case class Failure2(reason: String) extends Calculation2

object Calculator2 {
  def eval(ex: Expression): Calculation2 = ex match {
    case Number(n) => Success2(n)
    case Addition(l, r) => eval(l) match {
      case Success2(ll) => eval(r) match {
        case Success2(rr) => Success2(ll + rr)
        case fail => fail
      }
      case fail => fail
    }
    case Addition(l, r) => eval(l) match {
      case Success2(ll) => eval(r) match {
        case Success2(rr) => Success2(ll - rr)
        case fail => fail
      }
      case fail => fail
    }
  }
}

