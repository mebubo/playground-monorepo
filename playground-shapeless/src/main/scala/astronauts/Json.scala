package astronauts

import shapeless.labelled.FieldType
import shapeless.{:+:, CNil, Coproduct, HList, HNil, Inl, Inr, LabelledGeneric, Lazy, Witness, :: => :::}

sealed trait JsonValue

final case class JsonObject(fields: List[(String, JsonValue)]) extends JsonValue
final case class JsonArray(items: List[JsonValue]) extends JsonValue
final case class JsonString(value: String) extends JsonValue
final case class JsonNumber(value: Double) extends JsonValue
final case class JsonBoolean(value: Boolean) extends JsonValue
case object JsonNull extends JsonValue

trait JsonEncoder[A] {
  def encode(a: A): JsonValue
}

trait JsonObjectEncoder[A] extends JsonEncoder[A] {
  def encode(a: A): JsonObject
}

object JsonEncoder {
  def apply[A](implicit enc: JsonEncoder[A]): JsonEncoder[A] = enc

  def createEncoder[A](f: A => JsonValue): JsonEncoder[A] =
    new JsonEncoder[A] {
      override def encode(a: A): JsonValue = f(a)
    }

  def createObjectEncoder[A](f: A => JsonObject): JsonObjectEncoder[A] =
    new JsonObjectEncoder[A] {
      override def encode(a: A): JsonObject = f(a)
    }

  implicit val stringEncoder: JsonEncoder[String] = createEncoder(a => JsonString(a))
  implicit val doubleEncoder: JsonEncoder[Double] = createEncoder(a => JsonNumber(a))
  implicit val intEncoder: JsonEncoder[Int] = createEncoder(a => JsonNumber(a))
  implicit val booleanEncoder: JsonEncoder[Boolean] = createEncoder(a => JsonBoolean(a))

  implicit def listEncoder[A](implicit aEnc: JsonEncoder[A]): JsonEncoder[List[A]] =
    createEncoder(as => JsonArray(as.map(aEnc.encode)))

  implicit def optionEncoder[A](implicit aEnc: JsonEncoder[A]): JsonEncoder[Option[A]] =
    createEncoder(o => o.map(aEnc.encode).getOrElse(JsonNull))

  implicit val hNilEncoder: JsonObjectEncoder[HNil] =
    createObjectEncoder(_ => JsonObject(Nil))

  implicit def hListEncoder[K <: Symbol, H, T <: HList]
    (implicit
     witness: Witness.Aux[K],
     hEnc: Lazy[JsonEncoder[H]],
     tEnc: JsonObjectEncoder[T]): JsonObjectEncoder[FieldType[K, H] ::: T] = {
    val name = witness.value.name
    createObjectEncoder { case(h ::: t) =>
      val head = hEnc.value.encode(h)
      val tail = tEnc.encode(t)
      JsonObject((name, head) :: tail.fields)
    }
  }

  implicit val cNilEncoder: JsonObjectEncoder[CNil] =
    createObjectEncoder(_ => ???)

  implicit def coproductEncoder[K <: Symbol, H, T <: Coproduct]
  (implicit
   witness: Witness.Aux[K],
   hEnc: Lazy[JsonEncoder[H]],
   tEnc: JsonObjectEncoder[T]): JsonObjectEncoder[FieldType[K, H] :+: T] = {
    val name = witness.value.name
    createObjectEncoder {
      case Inl(h) => JsonObject(List(name -> hEnc.value.encode(h)))
      case Inr(t) => tEnc.encode(t)
    }
  }

  implicit def genericObjectEncoder[A, H]
    (implicit
     gen: LabelledGeneric.Aux[A, H],
     hEnc: Lazy[JsonObjectEncoder[H]]
    ): JsonEncoder[A] =
    createObjectEncoder(a => hEnc.value.encode(gen.to(a)))

}

object JsonEncoderMain {
  def main(args: Array[String]): Unit = {
    println("foo")
    val i = JsonEncoder[IceCream].encode(IceCream("foo", 1, true))
    val circ = JsonEncoder[Shape].encode(Circle(1))
    val rect = JsonEncoder[Shape].encode(Rectangle(1, 2))
    val c = LabelledGeneric[Shape]
    val x: Double = 1.1
    val xx = JsonEncoder[Double]
    println(xx.encode(x))
    println(i)
    println(circ)
    println(rect)
  }
}
