load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")
scala_import(
    name = "doobie_core",
    exports = [
        "//3rdparty/jvm/com/chuusai:shapeless",
        "//3rdparty/jvm/com/lihaoyi:sourcecode",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/scala_lang:scala_reflect",
        ":doobie_free"
    ],
    jars = [
        "//external:jar/org/tpolecat/doobie_core_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)



scala_import(
    name = "doobie_free",
    exports = [
        "//3rdparty/jvm/co/fs2:fs2_core",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/typelevel:cats_core",
        "//3rdparty/jvm/org/typelevel:cats_free"
    ],
    jars = [
        "//external:jar/org/tpolecat/doobie_free_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)



scala_import(
    name = "doobie_postgres",
    exports = [
        "//3rdparty/jvm/co/fs2:fs2_io",
        "//3rdparty/jvm/org/postgresql:postgresql",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":doobie_core"
    ],
    jars = [
        "//external:jar/org/tpolecat/doobie_postgres_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)



scala_import(
    name = "doobie_specs2",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/specs2:specs2_core",
        ":doobie_core"
    ],
    jars = [
        "//external:jar/org/tpolecat/doobie_specs2_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)


