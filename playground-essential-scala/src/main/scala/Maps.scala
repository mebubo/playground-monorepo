import scala.collection.immutable

object Maps {
  val example: Map[String, Int] = Map("a" -> 1, "b" -> 2)
  val a: Map[String, Int] = example.map { case (k, v) => k -> 2 * v }
  val strings: immutable.Iterable[String] = example.map(pair => "hello")

  def main(args: Array[String]): Unit = {
    println(x)
    println(y)
  }

  val x = a.flatMap { case (k, v) =>
    (1 to 3).map(x => (k + v) -> (k * v))
  }

  val y = for {
    (k, v) <- a
    x <- 1 to 3
  } yield (k + v) + (k * v)
}

object Ex {
  val people = Set(
    "Alice",
    "Bob",
    "Charlie",
    "Derek",
    "Edith",
    "Fred")

  val ages = Map(
    "Alice" -> 20,
    "Bob" -> 30,
    "Charlie" -> 50,
    "Derek" -> 40,
    "Edith" -> 10,
    "Fred" -> 60)

  val favoriteColors = Map(
    "Bob" -> "green",
    "Derek" -> "magenta",
    "Fred" -> "yellow")

  val favoriteLolcats = Map(
    "Alice" -> "Long Cat",
    "Charlie" -> "Ceiling Cat",
    "Edith" -> "Cloud Cat")

  def favoriteColor(n: String): Option[String] =
    favoriteColors.get(n)

  def favoriteColorOrBeige(n: String): String =
    favoriteColor(n).getOrElse("beige")

  def printColors(): Unit =
    people.foreach { p => println(s"$p's favorite color: ${favoriteColor(p)}") }

  def lookup[K, V](k: K, m: Map[K, V]): Option[V] =
    m get k

  def oldest(): Option[String] =
    if (ages.isEmpty)
      None
    else
      Some(ages.maxBy { case (k, v) => v }._1)

  val colorOfTheOldestPerson: Option[String] = for {
    o <- oldest()
    c <- favoriteColor(o)
  } yield c

  def main(args: Array[String]): Unit = {
    printColors()
    println(colorOfTheOldestPerson)
  }
}

object Ex2 {
  def union[K, K1 <: K, K2 <: K](s1: Set[K1], s2: Set[K2]): Set[K] =
    for {
      x <- s1
      y <- s2
      z <- List(x, y)
    } yield z

  def union2[K](s1: Set[K], s2: Set[K]): Set[K] =
    s1.foldLeft(s2){ (s, e) => s + e }

  val s1: Set[Int] = Set(1, 2, 3, 1)
  val s2: Set[String] = Set("foo", "bar")

  def main(args: Array[String]): Unit = {
    println(union(s1, s2))
    (10 until 1) ++ (20 until 25)
  }
}
