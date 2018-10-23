scalaVersion in ThisBuild := "2.12.6"

scalacOptions in ThisBuild ++= Seq(
  /* "-Xfatal-warnings", */
  "-Ypartial-unification"
)
val doobieVersion = "0.6.0"
val http4sVersion = "0.19.0-M4"
val doobieCore = "org.tpolecat" %% "doobie-core" % doobieVersion
val doobiePostgres = "org.tpolecat" %% "doobie-postgres" % doobieVersion
val doobieSpecs2 = "org.tpolecat" %% "doobie-specs2" % doobieVersion
val sqliteJdbc = "org.xerial" % "sqlite-jdbc" % "3.23.1"
val http4sDsl = "org.http4s" %% "http4s-dsl" % http4sVersion
val http4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % http4sVersion
val http4sBlazeClient = "org.http4s" %% "http4s-blaze-client" % http4sVersion
val magnolia =  "com.propensive" %% "magnolia" % "0.9.1"
val catsCore = "org.typelevel" %% "cats-core" % "1.4.0"
val catsMtl = "org.typelevel" %% "cats-mtl-core" % "0.4.0"
val shapeless =   "com.chuusai" %% "shapeless" % "2.3.3"
val simulacrum = "com.github.mpilquist" %% "simulacrum" % "0.12.0"
val scalazCore = "org.scalaz" %% "scalaz-core" % "7.2.26"
val refinedScalaz = "eu.timepit" %% "refined-scalaz" % "0.9.0"
val sprayJsonDerivation = "xyz.driver" %% "spray-json-derivation" % "0.4.5"
val scalatest = "org.scalatest" %% "scalatest" % "3.0.5" % "test"

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
    shapelessPlayground,
    essentialScala,
    fpmortals
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

lazy val essentialScala = p(
  "playground-essential-scala",
  Seq(
  )
)

lazy val fpmortals = p(
  "playground-fpmortals",
  Seq(
    simulacrum,
    scalazCore,
    refinedScalaz,
    sprayJsonDerivation,
    scalatest
  )
)
  .settings(
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
    scalacOptions ++= Seq(
      "-language:_",
      "-Ypartial-unification",
      "-Xfatal-warnings"
    )
  )
