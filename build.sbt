scalaVersion in ThisBuild := "2.12.6"

scalacOptions in ThisBuild ++= Seq(
  /* "-Xfatal-warnings", */
  "-Ypartial-unification"
)
val doobieVersion = "0.5.3"
val http4sVersion = "0.18.12"

val doobieCore = "org.tpolecat" %% "doobie-core" % doobieVersion
val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % doobieVersion
val doobieSpecs2 = "org.tpolecat" %% "doobie-specs2" % doobieVersion
val sqliteJdbc = "org.xerial" % "sqlite-jdbc" % "3.23.1"

val http4sDsl = "org.http4s" %% "http4s-dsl" % http4sVersion
val http4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % http4sVersion
val http4sBlazeClient = "org.http4s" %% "http4s-blaze-client" % http4sVersion

val magnolia =  "com.propensive" %% "magnolia" % "0.7.1"

val catsCore = "org.typelevel" %% "cats-core" % "1.1.0"

val shapeless =   "com.chuusai" %% "shapeless" % "2.3.3"

def p(n: String, deps: Seq[ModuleID]): Project = {
  Project(n, file(n))
    .enablePlugins(ReproducibleBuildsPlugin)
    .enablePlugins(PackPlugin)
    .settings(
      name := n,
      libraryDependencies ++= deps
    )
}

lazy val root = project
  .in(file("."))
  .aggregate(
    doobie,
    http4s,
    magnoliaPlayground,
    scalaWithCats,
    shapelessPlayground
  )

lazy val doobie = p(
  "playground-doobie",
  Seq(
    doobieCore,
    doobiePostgres,
    doobieSpecs2,
    sqliteJdbc
  )
)

lazy val http4s = p(
  "playground-http4s",
  Seq(
    http4sDsl,
    http4sBlazeServer,
    http4sBlazeClient
  )
)

lazy val magnoliaPlayground = p(
  "playground-magnolia",
  Seq(
    magnolia
  )
)

lazy val scalaWithCats = p(
  "playground-scala-with-cats",
  Seq(
    catsCore
  )
)

lazy val shapelessPlayground = p(
  "playground-shapeless",
  Seq(
    catsCore,
    shapeless
  )
)
