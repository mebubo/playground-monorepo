case class Cat(color: String, favoriteFood: String) {

}

object ChipShop {
  def willServe(cat: Cat): Boolean = cat match {
    case Cat(_, "Chips") => true
    case Cat(_, _) => false
  }
}
