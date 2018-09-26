import scala.collection.mutable

object Animals {
  def main(args: Array[String]): Unit = {
    val animals: Seq[String] = Seq("cat", "dog", "penguin")
    "mouse" +: animals :+ "tyrannosaurus"
    val x: Seq[Any] = animals :+ 2

    val animals2: mutable.Seq[String] = mutable.IndexedSeq[String]("cat", "dog", "penguin")
    animals2.update(1, "1")

    println(animals2)
  }
}
