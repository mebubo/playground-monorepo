load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")

scala_library(name = "playground_doobie",
              deps = [
                  "//3rdparty/jvm/org/tpolecat:doobie_core",
                  "//3rdparty/jvm/org/typelevel:cats_core",
                  "//3rdparty/jvm/org/typelevel:cats_effect"
              ],
              srcs = glob(["src/main/scala/**/D1.scala"]),
              visibility = ["//visibility:public"],
              scalacopts = ["-Ypartial-unification"])

