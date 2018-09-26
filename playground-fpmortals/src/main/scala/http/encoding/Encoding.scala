package http.encoding

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Url
import http.oauth2.client.api.{AccessRequest, AuthRequest, RefreshRequest}
import simulacrum.typeclass

final case class UrlQuery(params: List[(String, String)]) {
  def forUrl(url: String Refined Url): String Refined Url = ???
}

@typeclass trait UrlQueryWriter[A] {
  def toUrlQuery(a: A): UrlQuery
}

@typeclass trait UrlEncodedWriter[A] {
  def toUrlEncoded(a: A): String
}

object UrlEncodedWriter {

  import ops._

  implicit val string: UrlEncodedWriter[String] = { s => java.net.URLEncoder.encode(s, "UTF-8") }
  implicit val long: UrlEncodedWriter[Long] = _.toString
  implicit val stringySeq: UrlEncodedWriter[Seq[(String, String)]] =
    _.map { case (k, v) => s"${k.toUrlEncoded}=${v.toUrlEncoded}" }.mkString("&")
  implicit val url: UrlEncodedWriter[String Refined Url] = { s => java.net.URLEncoder.encode(s.value, "UTF-8") }
}

import UrlEncodedWriter.ops._

object AuthRequestOps {
  private def stringify[T: UrlEncodedWriter](t: T) =
    java.net.URLDecoder.decode(t.toUrlEncoded, "UTF-8")

  implicit val query: UrlQueryWriter[AuthRequest] = { a =>
    UrlQuery(List(
      "redirect_uri" -> stringify(a.redirect_uri),
      "scope" -> stringify(a.scope),
      "client_id" -> stringify(a.client_id),
      "prompt" -> stringify(a.prompt),
      "response_type" -> stringify(a.response_type),
      "access_type" -> stringify(a.access_type)))
  }
}

object AccessRequestOps {
  implicit val encoded: UrlEncodedWriter[AccessRequest] = { a =>
    Seq(
      "code" -> a.code.toUrlEncoded,
      "redirect_uri" -> a.redirect_uri.toUrlEncoded,
      "client_id" -> a.client_id.toUrlEncoded,
      "client_secret" -> a.client_secret.toUrlEncoded,
      "scope" -> a.scope.toUrlEncoded,
      "grant_type" -> a.grant_type.toUrlEncoded
    ).toUrlEncoded
  }
}

object RefreshRequestOps {
  implicit val encoded: UrlEncodedWriter[RefreshRequest] = { r =>
    Seq(
      "client_secret" -> r.client_secret.toUrlEncoded,
      "refresh_token" -> r.refresh_token.toUrlEncoded,
      "client_id" -> r.client_id.toUrlEncoded,
      "grant_type" -> r.grant_type.toUrlEncoded
    ).toUrlEncoded
  }
}