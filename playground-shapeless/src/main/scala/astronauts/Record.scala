package astronauts

import shapeless._
import shapeless.record._

case class IceCream2(name: String, numCherries: Int, inCone: Boolean)

object Record {
  val sundae = LabelledGeneric[IceCream].to(IceCream("Sundae", 1, false))

  def main(args: Array[String]): Unit = {
    val name: String = sundae.get('name)
    println(name)
    val s2: IceCream = LabelledGeneric[IceCream].from(sundae.updated('numCherries, 3))
    val s3 = sundae.remove('inCone)
    println(s2)
    println(s3)
    println(sundae.toMap)
  }
}
