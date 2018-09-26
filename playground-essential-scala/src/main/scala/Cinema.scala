object Cinema {

  val eastwood = new Director("Clint", "Eastwood", 1930)
  val mcTiernan = new Director("John", "McTiernan", 1951)
  val nolan = new Director("Christopher", "Nolan", 1970)
  val someBody = new Director("Just", "Some Body", 1990)

  val memento: Moovie = Moovie("Memento", 2000, 8.5, nolan)
  val darkKnight = Moovie("Dark Knight", 2008, 9.0, nolan)
  val inception = Moovie("Inception", 2010, 8.8, nolan)

  val highPlainsDrifter = Moovie("High Plains Drifter", 1973, 7.7, eastwood)
  val outlawJoseyWales = Moovie("The Outlaw Josey Wales", 1976, 7.9, eastwood)
  val unforgiven = Moovie("Unforgiven", 1992, 8.3, eastwood)
  val granTorino = Moovie("Gran Torino", 2008, 8.2, eastwood)
  val invictus = Moovie("Invictus", 2009, 7.4, eastwood)

  val predator = Moovie("Predator", 1987, 7.9, mcTiernan)
  val dieHard = Moovie("Die Hard", 1988, 8.3, mcTiernan)
  val huntForRedOctober = Moovie("The Hunt for Red October", 1990, 7.6, mcTiernan)
  val thomasCrownAffair = Moovie("The Thomas Crown Affair", 1999, 6.8, mcTiernan)

  def main(args: Array[String]): Unit = {
    println(eastwood.yearOfBirth) // should be 1930
    println(dieHard.director.name) // should be "John McTiernan"
    println(invictus.isDirectedBy(nolan)) // should be false
    println(highPlainsDrifter.copy(name = "L'homme des hautes plaines"))
    // returns Film("L'homme des hautes plaines", 1973, 7.7, /* etc */)
    println(thomasCrownAffair.copy(yearOfRelease = 1968,
      director = new Director("Norman", "Jewison", 1926)))
    // returns Film("The Thomas Crown Affair", 1926, /* etc */)
    println(inception.copy().copy().copy())

    val d1 = new Director("a", "b", 0)
    val d2 = new Director("a", "b", 0)
    println(d1 == d2)
    println(d1.equals(d2))
  }

}

case class Moovie(name: String, yearOfRelease: Int, imdbRating: Double, director: Director) {
  def directorsAge: Int = {
    val birth = director.yearOfBirth
    yearOfRelease - birth
  }
  def isDirectedBy(other: Director): Boolean = director.equals(other)
}

case class Director(firstName: String, lastName: String, yearOfBirth: Int) {
  def name = s"$firstName $lastName"
}

object Director {
  def older(d1: Director, d2: Director): Director =
    if (d1.yearOfBirth > d2.yearOfBirth) d2 else d1
}

object Dad {
  val f: Int => Int = (a: Int) => 1
}

