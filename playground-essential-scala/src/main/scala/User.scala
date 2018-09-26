import java.util.Date
import JsWriter._

sealed trait Visitor {
  def id: String

  def createdAt: Date

  def age: Long = new Date().getTime - createdAt.getTime
}

object Visitor {
  val anonymousWriter: JsWriter[Anonymous] = new JsWriter[Anonymous] {
    override def write(a: Anonymous): JsValue =
      JsObject(Map(
        "id" -> a.id.toJson,
        "createdAt" -> a.createdAt.toJson
      ))
  }
  val userWriter: JsWriter[User] = new JsWriter[User] {
    override def write(a: User): JsValue =
      JsObject(Map(
        "id" -> a.id.toJson,
        "email" -> a.email.toJson,
        "createdAt" -> a.createdAt.toJson
      ))
  }

  implicit val visitorWriter: JsWriter[Visitor] = new JsWriter[Visitor] {
    override def write(a: Visitor): JsValue = a match {
      case anon: Anonymous => anonymousWriter.write(anon)
      case user: User => userWriter.write(user)
    }
  }
}

final case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor

final case class User(id: String, email: String, createdAt: Date = new Date()) extends Visitor

object VisitorTest {
  def main(args: Array[String]): Unit = {
    val visitors: Seq[Visitor] = Seq(Anonymous("001", new Date), User("003", "dave@xample.com", new Date))
    val strs = visitors map (v => v.toJson.stringify)
    println(strs)
    println(List("foo", "bar"))
  }
}
