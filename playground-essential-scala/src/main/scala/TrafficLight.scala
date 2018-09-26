sealed trait TrafficLight {
  def position: String
}

case object Red extends TrafficLight {
  val position = "top"
}
case object Yellow extends TrafficLight {
  override val position = "middle"
}
case object Green extends TrafficLight {
  override val position: String = "bottom"
}
