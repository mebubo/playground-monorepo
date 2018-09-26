class Foo(val first: String, last: String) {
  println("Foo")
}

object MethodCallVsPass {

  def foo(): Unit = println("foo")
  def bar: Unit = println("bar")

  lazy val xxx: Unit = println("xxx")
  val yyy: Unit = println("yyy")

  def plus2(n: Int): Int = n + 2

  def main(args: Array[String]): Unit = {
    val a: Unit = foo()
    val b: () => Unit = foo
    val c: Unit = foo
    val d = foo
    bar
    val x: MethodCallVsPass.type = MethodCallVsPass
    println(MethodCallVsPass)
    List(1,2,3).map(plus2)
  }

  def foox = new Foo("aaa", "bbb")
  foox.first
}
