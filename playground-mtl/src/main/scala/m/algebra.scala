package m

trait Config[F[_]] {
  def getUserName: F[String]
}

trait Console[F[_]] {
  def readLine: F[String]
  def writeLine(s: String): F[Unit]
}

trait State[F[_], A] {
  def save(s: A)
}
