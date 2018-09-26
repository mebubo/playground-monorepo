class A
class B

def foo(implicit a: A) = 1
implicit def bar(implicit b: B): A = new A
implicit val b: B = new B

foo