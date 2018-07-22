load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")

deps = ["//3rdparty/jvm/org/http4s:http4s_dsl"]

scala_library(name = "h",
              deps = deps,
              srcs = glob(["**/*.scala"]),
              scalacopts = ["-Ypartial-unification"]
              )
