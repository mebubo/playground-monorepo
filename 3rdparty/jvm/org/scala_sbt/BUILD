load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")
java_library(
    name = "test_interface",
    exports = [
        "//external:jar/org/scala_sbt/test_interface"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)


