package functor

import cats.{Contravariant, Show}
import cats.instances.string._

object Contravar {

  val showString: Show[String] = Show[String]

  val showSymbol: Show[Symbol] = Contravariant[Show].contramap(showString)((sym: Symbol) => s"'${sym.name}")

  def main(args: Array[String]): Unit = {
    println(showSymbol.show('dave))
  }

}
