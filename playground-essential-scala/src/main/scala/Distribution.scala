final case class Distribution[A](l: List[(A, Double)]) {
  def map[B](f: A => B): Distribution[B] = Distribution(l.map { case (a, p) => (f(a), p) })

  def flatMap[B](f: A => Distribution[B]): Distribution[B] = {
    val xs: List[(B, Double)] = l.flatMap { case (a, p) => f(a).l.map { case (b, p2) => (b, p * p2) } }
    Distribution(xs).normalize
  }

  def normalize: Distribution[A] = {
    val totalWeight: Double = l.map(_._2).sum
    Distribution(l.map { case (a, p) => (a, p / totalWeight) })
  }

  def compact: Distribution[A] = {
    val distinct: List[A] = l.map(_._1).distinct

    def prob(a: A): Double = (l filter { case (x, p) => x == a } map (_._2)).sum

    Distribution(distinct.map { a => (a, prob(a)) })
  }
}

object Distribution {
  def uniform[A](l: List[A]): Distribution[A] = {
    val weight: Double = 1.0 / l.size
    Distribution(l.map(a => (a, weight)))
  }

  def discrete[A](events: List[(A, Double)]): Distribution[A] =
    Distribution(events).compact.normalize
}

sealed trait Coin

final case object Heads extends Coin

final case object Tails extends Coin

object Coin2 {
  val fairCoin: Distribution[Coin] = Distribution.uniform(List(Heads, Tails))

  def threeFlips(): Distribution[(Coin, Coin, Coin)] = for {
    f1 <- fairCoin
    f2 <- fairCoin
    f3 <- fairCoin
  } yield (f1, f2, f3)

  def main(args: Array[String]): Unit = {
    println(threeFlips())
  }
}

object Cat2 {

  sealed trait Food

  final case object Raw extends Food

  final case object Cooked extends Food

  val food: Distribution[Food] =
    Distribution.discrete(List(Cooked -> 0.3, Raw -> 0.7))

  sealed trait Cat

  final case object Asleep extends Cat

  final case object Harassing extends Cat

  def cat(food: Food): Distribution[Cat] =
    food match {
      case Cooked => Distribution.discrete(List(Harassing -> 0.8, Asleep -> 0.2))
      case Raw => Distribution.discrete(List(Harassing -> 0.4, Asleep -> 0.6))
    }

  val foodModel: Distribution[(Food, Cat)] =
    for {
      f <- food
      c <- cat(f)
    } yield (f, c)

  // Probability the cat is harassing me
  val pHarassing: Double =
    foodModel.l filter {
      case ((_, Harassing), _) => true
      case ((_, Asleep), _) => false
    } map { case (a, p) => p } sum

  // Probability the food is cooked given the cat is harassing me
  val pCookedGivenHarassing: Option[Double] =
    foodModel.l find {
      case ((Cooked, Harassing), _) => true
      case _ => false
    } map { case ((_, _), p) => p / pHarassing}


  def main(args: Array[String]): Unit = {
    println(pCookedGivenHarassing)
  }
}
