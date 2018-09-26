import java.util.Date

sealed trait JsValue {
  def stringify: String
}

final case class JsObject(values: Map[String, JsValue]) extends JsValue {
  def stringify = values
    .map { case (name, value) => "\"" + name + "\":" + value.stringify }
    .mkString("{", ",", "}")
}

final case class JsString(value: String) extends JsValue {
  def stringify = "\"" + value.replaceAll("\\|\"", "\\\\$1") + "\""
}

trait JsWriter[A] {
  def write(a: A): JsValue

}

object JsWriter {

  implicit class JsUtil[A](a: A) {
    def toJson(implicit w: JsWriter[A]): JsValue = w.write(a)
  }

  implicit object StringWriter extends JsWriter[String] {
    def write(value: String) = JsString(value)
  }

  implicit object DateWriter extends JsWriter[Date] {
    def write(value: Date) = JsString(value.toString)
  }

}
