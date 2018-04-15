scalaVersion in ThisBuild := "2.12.4"
scalacOptions in ThisBuild ++= Seq(
  "-language:_",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)

libraryDependencies ++= Seq(
  "com.github.mpilquist" %% "simulacrum" % "0.12.0",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.scalaz" %% "scalaz-core" % "7.2.19"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

wartremoverErrors ++= Warts.unsafe
