package functor

trait Codec[A] { self =>
  def encode(a: A): String
  def decode(s: String): A

  def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
    override def encode(b: B): String = self.encode(enc(b))
    override def decode(s: String): B = dec(self.decode(s))
  }
}

object Codec {
  def encode[A](a: A)(implicit codec: Codec[A]): String =
    codec.encode(a)
  def decode[A](s: String)(implicit codec: Codec[A]): A =
    codec.decode(s)

  implicit val stringCodec: Codec[String] = new Codec[String] {
    override def encode(a: String): String = a
    override def decode(s: String): String = s
  }

  implicit val intCodec: Codec[Int] = stringCodec.imap(_.toInt, _.toString)
  implicit val booleanCodec: Codec[Boolean] = stringCodec.imap(_.toBoolean, _.toString)
  implicit val doubleCodec: Codec[Double] = stringCodec.imap(_.toDouble, _.toString)

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap(Box(_), _.value)
}

case class Box[A](value: A)

object M2 {
  def main(args: Array[String]): Unit = {
    println(Codec.encode(1))
    println(Codec.decode[Int]("123"))
    println(Codec.decode[Double]("123"))
    println(Codec.encode(Box("123")))
    println(Codec.decode[Box[Int]]("123"))
  }
}

