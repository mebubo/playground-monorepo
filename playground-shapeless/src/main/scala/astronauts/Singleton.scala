package astronauts

import shapeless.Witness
import shapeless.labelled.FieldType
import shapeless.syntax.SingletonOps
import shapeless.syntax.singleton._

object Singleton {
  1.narrow
  true.narrow
  "hello".narrow

  trait Cherries

  val number = 42
//  val numCheries: Int with Cherries = number.asInstanceOf[Int with Cherries]
  val numCherries = "numCherries!!!" ->> 123

  def getFieldName[K, V](value: FieldType[K, V])(implicit witness: Witness.Aux[K]): K = witness.value

  def getFieldValue[K, V](value: FieldType[K, V]): V = value

  private val numCherriesFieldName = getFieldName(numCherries)
  private val numCherriesFieldValue = getFieldValue(numCherries)

  def main(args: Array[String]): Unit = {
    println(numCherriesFieldName)
    println(numCherriesFieldValue)
  }
}
