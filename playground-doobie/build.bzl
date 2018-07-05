load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")

deps = [ "//3rdparty/jvm/org/tpolecat:doobie_core",
        "//3rdparty/jvm/org/tpolecat:doobie_postgres",
        "//3rdparty/jvm/org/typelevel:cats_core",
        "//3rdparty/jvm/org/typelevel:cats_effect"
        ]


def binary(name, src):
        scala_binary(name = name,
                     deps = deps,
                     runtime_deps = [ "//3rdparty/jvm/org/xerial:sqlite_jdbc" ],
                     srcs = native.glob([src]),
                     main_class = "d.%s" % name,
                     visibility = ["//visibility:public"],
                     scalacopts = ["-Ypartial-unification"]
                     )
