case class Counter(n: Int = 0) {
  def inc: Counter = inc()
  def dec: Counter = dec()
  def inc(d: Int = 1): Counter = copy(n + d)
  def dec(d: Int = 1): Counter = copy(n - d)
  def adjust(adder: Adder): Counter = copy(n = adder(n))
}

class Adder(amount: Int) {
  def apply(in: Int): Int = amount + in

  def main(args: Array[String]): Unit = {

  }
}

object Counter {
  def main(args: Array[String]): Unit = {
    println(new Counter(0).inc.inc(10).inc.dec(3))
  }
}
