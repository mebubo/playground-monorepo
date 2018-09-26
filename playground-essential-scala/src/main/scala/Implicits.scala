object Implicits {

  implicit val ordering: Ordering[Int] = Ordering.fromLessThan(_ > _)

//  import scala.math.Ordering

  val minOrdering: Ordering[Int] = Ordering.fromLessThan[Int](_ < _)
  // minOrdering: scala.math.Ordering[Int] = scala.math.Ordering$$anon$9@787f32b7

  val maxOrdering: Ordering[Int] = Ordering.fromLessThan[Int](_ > _)
  // maxOrdering: scala.math.Ordering[Int] = scala.math.Ordering$$anon$9@4bf324f9

  List(3, 4, 2).sorted(minOrdering)
  // res: List[Int] = List(2, 3, 4)

  List(3, 4, 2).sorted(maxOrdering)
  // res: List[Int] = List(4, 3, 2)


  def main(args: Array[String]): Unit = {


    val sorted = List(3, 5, 2).sorted
    println(sorted)
  }
}
