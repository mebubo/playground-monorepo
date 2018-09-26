scalaVersion in ThisBuild := "2.12.6"
scalacOptions in ThisBuild += "-Ypartial-unification"

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

lazy val root = project
    .in(file("."))
    .aggregate(
        doobie,
        http4s
    )

lazy val doobie = project
    .in(file("playground-doobie"))
    .settings(
        name := "playground-doobie",
        libraryDependencies ++= Seq(
            doobieCore,
            doobiePostgres,
            doobieSpecs2,
            sqliteJdbc
        )
    )

lazy val http4s = project
    .in(file("playground-http4s"))
    .settings(
        name := "playground-http4s",
        libraryDependencies ++= Seq(
            http4sDsl,
            http4sBlazeServer,
            http4sBlazeClient
        )
    )

lazy val magnoliaPlayground = project
    .in(file("playground-magnolia"))
    .settings(
        name := "playground-magnolia",
        libraryDependencies ++= Seq(
            magnolia
        )
    )
