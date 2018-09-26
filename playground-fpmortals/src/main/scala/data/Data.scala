package data

import eu.timepit.refined
import refined.numeric.Positive
import refined.api.{Refined, Validate}
import refined.collection.NonEmpty

object Data {

  type |:[A, B] = Either[A, B]

  type Accepted = String |: Long |: Boolean

  import refined.auto._

  val sam: String Refined NonEmpty = "Sam"
//  val empty: String Refined NonEmpty = ""

  import refined.W
  import refined.boolean.And
  import refined.collection.MaxSize

  type Name = NonEmpty And MaxSize[W.`10`.T]

  final case class Person(
                           name: String Refined Name,
                           age: Int Refined Positive
                         )


  final case class Url()
  object Url {
    implicit def urlValidate: Validate.Plain[String, Url] =
      Validate.fromPartial(new java.net.URL(_), "Url", Url())
  }

//  val u: String Refined Url = "http://hello.com"
}
