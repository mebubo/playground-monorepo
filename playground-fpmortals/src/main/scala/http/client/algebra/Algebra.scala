package http.client.algebra

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Url
import spray.json.JsonReader
import http.encoding.UrlEncodedWriter

final case class HttpHeader(k: String, v: String)
final case class HttpResponseHeader(k: String, v: String)

final case class Response[T](header: HttpResponseHeader, body: T)

trait JsonHttpClient[F[_]] {
  def get[B: JsonReader](
    uri: String Refined Url,
    headers: List[HttpHeader] = Nil
  ): F[Response[B]]

  def postUrlencoded[A: UrlEncodedWriter, B: JsonReader](
    uri: String Refined Url,
    payload: A,
    headers: List[HttpHeader] = Nil
  ): F[Response[B]]
}