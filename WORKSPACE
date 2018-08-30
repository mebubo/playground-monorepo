git_repository(
    name = "io_bazel_rules_scala",
    remote = "git://github.com/bazelbuild/rules_scala",
    commit = "57cc474bea9d30884cca343c0f5a569b2698424e" # update this as needed
)

load("//3rdparty:workspace.bzl", "maven_dependencies")
maven_dependencies()

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")
scala_repositories()

load("@io_bazel_rules_scala//scala:toolchains.bzl", "scala_register_toolchains")
scala_register_toolchains()
