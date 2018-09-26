package http.oauth2.client

import java.time.temporal.ChronoUnit

import http.client.{BearerToken, RefreshToken, ServerConfig}
import http.client.algebra.JsonHttpClient
import http.encoding.UrlQueryWriter.ops._
import http.oauth2.client.algebra.{CodeToken, LocalClock, UserInteraction}
import http.oauth2.client.api._
import scalaz._
import Scalaz._
import spray.json.ImplicitDerivedJsonProtocol._
import http.encoding.AuthRequestOps._

class OAuth2Client[F[_]: Monad](
  config: ServerConfig
)(
  implicit
  user: UserInteraction[F],
  client: JsonHttpClient[F],
  clock: LocalClock[F]
) {
  def authenticate: F[CodeToken] =
    for {
      callback <- user.start
      params   = AuthRequest(callback, config.scope, config.clientId)
      _        <- user.open(params.toUrlQuery.forUrl(config.auth))
      code     <- user.stop
    } yield code

  def access(code: CodeToken): F[(RefreshToken, BearerToken)] = {
    import http.encoding.AccessRequestOps._
    for {
      request <- AccessRequest(code.token,
        code.redirect_uri,
        config.clientId,
        config.clientSecret).pure[F]
      response <- client.postUrlencoded[AccessRequest, AccessResponse](
        config.access, request)
      time    <- clock.now
      msg     = response.body
      expires = time.plus(msg.expires_in, ChronoUnit.SECONDS)
      refresh = RefreshToken(msg.refresh_token)
      bearer  = BearerToken(msg.access_token, expires)
    } yield (refresh, bearer)
  }

  def bearer(refresh: RefreshToken): F[BearerToken] = {
    import http.encoding.RefreshRequestOps._
    for {
      request <- RefreshRequest(config.clientSecret,
        refresh.token,
        config.clientId).pure[F]
      response <- client.postUrlencoded[RefreshRequest, RefreshResponse](
        config.refresh, request)
      time    <- clock.now
      msg     = response.body
      expires = time.plus(msg.expires_in, ChronoUnit.SECONDS)
      bearer  = BearerToken(msg.access_token, expires)
    } yield bearer
  }
}
