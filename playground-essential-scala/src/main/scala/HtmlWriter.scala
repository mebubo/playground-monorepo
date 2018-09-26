trait HtmlWriter[A] {
  def write(in: A): String
}

object HtmlWriter {
  def apply[A](implicit writer: HtmlWriter[A]): HtmlWriter[A] = writer
  def write[A](in: A)(implicit writer: HtmlWriter[A]): String = writer.write(in)
  implicit class HtmlOps[T](data: T) {
    def toHtml(implicit writer: HtmlWriter[T]): String = writer.write(data)
  }

}

object HtmlUtil {
  def htmlify[A](data: A)(implicit writer: HtmlWriter[A]): String = {
    writer.write(data)
  }

  implicit object ApproximationWriter extends HtmlWriter[Int] {
    override def write(in: Int): String = s"It's definitely less than ${in + 1}"
  }
}


object Main3 {
  def main(args: Array[String]): Unit = {
    println(HtmlUtil.htmlify(Person("name", "email")))
    import HtmlUtil.ApproximationWriter
    println(HtmlUtil.htmlify(1))
    println(HtmlWriter.write[Person](Person("n", "e")))
    println(HtmlWriter[Person].write(Person("n", "e")))
    import HtmlWriter.HtmlOps
    println(Person("nn", "ee").toHtml)
    implicitly[HtmlWriter[Person]]
  }
}

