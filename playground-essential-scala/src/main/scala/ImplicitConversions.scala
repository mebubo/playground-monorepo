object ImplicitConversions {

  class B {
    def bar = "This is the best method ever!"
  }

  class A

  implicit def aToB(in: A): B = new B()

  new A().bar
}
