package astronauts

import cats.Monoid
import cats.instances.all._
import shapeless.ops.hlist
import shapeless.{HList, HNil, LabelledGeneric, Lazy, ::}
import shapeless.labelled.{field, FieldType}

trait Migration[A, B] {
  def apply(a: A): B
}

object Migration {
  implicit class MigrationOps[A](a: A) {
    def migrateTo[B](implicit migr: Migration[A, B]): B = migr.apply(a)
  }

  implicit def genericMigration[A, B, ARepr <: HList, BRepr <: HList, Common <: HList,
    Added <: HList, Unaligned <: HList]
  (implicit
    aGen: LabelledGeneric.Aux[A, ARepr],
    bGen: LabelledGeneric.Aux[B, BRepr],
    inter: hlist.Intersection.Aux[ARepr, BRepr, Common],
    diff: hlist.Diff.Aux[BRepr, Common, Added],
    monoid: Monoid[Added],
    prepend: hlist.Prepend.Aux[Added, Common, Unaligned],
    align: hlist.Align[Unaligned, BRepr]
  ): Migration[A, B] = new Migration[A, B] {
    override def apply(a: A): B = bGen.from(align(prepend(monoid.empty, inter(aGen.to(a)))))
  }

  def createMonoid[A](zero: A)(add: (A, A) => A): Monoid[A] = new Monoid[A] {
    override def empty: A = zero
    override def combine(x: A, y: A): A = add(x, y)
  }

  implicit val hnilMonoid: Monoid[HNil] =
    createMonoid[HNil](HNil)((_, _) => HNil)

  implicit def hlistMonoid[K <: Symbol, H, T <: HList]
  (implicit
    hMonoid: Lazy[Monoid[H]],
    tMonoid: Monoid[T]
  ): Monoid[FieldType[K, H] :: T] =
    createMonoid(field[K](hMonoid.value.empty) :: tMonoid.empty) {
      (x, y) =>
        field[K](hMonoid.value.combine(x.head, y.head)) :: tMonoid.combine(x.tail, y.tail)
    }
}

case class IceCreamV1(name: String, numCherries: Int, inCone: Boolean)
case class IceCreamV2a(name: String, inCone: Boolean)
case class IceCreamV2b(name: String, inCone: Boolean, numCherries: Int)
case class IceCreamV2c(name: String, inCone: Boolean, numCherries: Int, numWaffles: Int)

object M23 {
  def main(args: Array[String]): Unit = {
    import Migration._
    val sundae = IceCreamV1("Sundae", 1, false)
    val s1 = sundae.migrateTo[IceCreamV2a]
    val s2 = sundae.migrateTo[IceCreamV2b]
    val s3 = sundae.migrateTo[IceCreamV2c]
    println(s1)
    println(s2)
    println(s3)
  }
}
