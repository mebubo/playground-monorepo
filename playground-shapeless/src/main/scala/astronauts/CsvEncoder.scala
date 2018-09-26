package astronauts

import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy}

trait CsvEncoder[A] {
  def encode(a: A): List[String]
}

object CsvEncoder {
  /*implicit*/ val employeeEncoder: CsvEncoder[Employee] = (a: Employee) =>
    List(a.name, a.number.toString, if (a.manager) "yes" else "no")

  /*implicit*/ val iceCreamEncoder: CsvEncoder[IceCream] = (i: IceCream) =>
    List(i.name, i.numCherries.toString, i.inCone.toString)

  implicit def pairEncoder[A, B](implicit encA: CsvEncoder[A], encB: CsvEncoder[B]): CsvEncoder[(A, B)] = {
    case (a, b) => encA.encode(a) ++ encB.encode(b)
  }

  implicit val stringEncoder: CsvEncoder[String] = (s: String) => List(s)
  implicit val intEncoder: CsvEncoder[Int] = (i: Int) => List(i.toString)
  implicit val doubleEncoder: CsvEncoder[Double] = (i: Double) => List(i.toString)
  implicit val booleanEncoder: CsvEncoder[Boolean] = (b: Boolean) => List(if (b) "yes" else "no")

  implicit val hnilEncoder: CsvEncoder[HNil] = (h: HNil) => List()

  implicit def hlistEncoder[H, T <: HList](implicit hEnc: Lazy[CsvEncoder[H]],
                                           tEnc: CsvEncoder[T]): CsvEncoder[H :: T] = instance {
      case h :: t => hEnc.value.encode(h) ++ tEnc.encode(t)
  }

  implicit val cnilEncoder: CsvEncoder[CNil] = cnil => throw new Exception("Inconcievable")

  implicit def coproductEncoder[H, T <: Coproduct](implicit hEnc: Lazy[CsvEncoder[H]], tEnc: CsvEncoder[T]): CsvEncoder[H :+: T] = instance {
    case Inl(h) => hEnc.value.encode(h)
    case Inr(t) => tEnc.encode(t)
  }

  /*implicit*/ val genericIceCreamEncoder: CsvEncoder[IceCream] = {
    val gen = Generic[IceCream]
    val enc = CsvEncoder[gen.Repr]
    instance((i: IceCream) => enc.encode(gen.to(i)))
  }

  implicit def genericEncoder[A, R](implicit gen: Generic[A] {type Repr = R}, enc: Lazy[CsvEncoder[R]]): CsvEncoder[A] = {
    instance((a: A) => enc.value.encode(gen.to(a)))
  }

  def writeCsv[A](as: List[A])(implicit enc: CsvEncoder[A]): String =
    as.map(a => enc.encode(a).mkString(",")).mkString("\n")

  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc

  def instance[A](f: A => List[String]): CsvEncoder[A] = (a: A) => f(a)
}

object M {

  val employees: List[Employee] = List(
    Employee("Bill", 1, true),
    Employee("Peter", 2, false),
    Employee("Milton", 3, false)
  )

  val iceCreams: List[IceCream] = List(
    IceCream("Sundae", 1, false),
    IceCream("Cornetto", 0, true),
    IceCream("Banana Split", 0, false)
  )

  val shapes: List[Shape] = List(
    Rectangle(3.0, 4.0),
    Circle(1.0)
  )

  def main(args: Array[String]): Unit = {
    import CsvEncoder._
    println(writeCsv(employees))
    println(writeCsv(iceCreams))
//    println(writeCsv(employees zip iceCreams))
    println(writeCsv(shapes))

    CsvEncoder[Tree[Int]]

    type X = String :: Int :: Boolean :: HNil
    println(CsvEncoder[X].encode(Generic[IceCream].to(iceCreams(0))))
  }
}
