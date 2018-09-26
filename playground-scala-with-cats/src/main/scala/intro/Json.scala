package intro

sealed trait Json
final case class JsonObject(get: Map[String, Json]) extends Json
final case class JsonString(get: String) extends Json
final case class JsonNumber(get: Double) extends Json
case object JsonNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] = (value: String) => JsonString(value)
  implicit val personWriter: JsonWriter[Person] = (value: Person) =>
    JsonObject(Map(
      "name" -> JsonString(value.name),
      "email" -> JsonString(value.email)
    ))
  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] =
    (value: Option[A]) => value match {
      case Some(x) => writer.write(x)
      case None => JsonNull
    }
}

object Json {
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json =
    writer.write(value)
}

object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit writer: JsonWriter[A]): Json =
      writer.write(value)
  }
}

object Main {
  import JsonWriterInstances._


  def main(args: Array[String]): Unit = {
    val person = Person("foo", "bar")
    interfaceObject(person)
    interfaceSyntax(person)
    usingImplicitly(person)
  }

  private def interfaceObject(person: Person): Unit = {
    val json = Json.toJson(person)
    println(json)
    println(Json.toJson("foo"))
    println(Json.toJson(Option("bar")))
    println(Json.toJson[Option[String]](None))
  }

  private def interfaceSyntax(person: Person): Unit = {
    import JsonSyntax.JsonWriterOps
    val json = person.toJson
    println(json)
  }

  def usingImplicitly(person: Person): Unit = {
    val json = implicitly[JsonWriter[Person]].write(person)
    println(json)
  }
}
