load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")
scala_import(
    name = "specs2_common",
    exports = [
        "//3rdparty/jvm/org/scala_lang/modules:scala_parser_combinators",
        "//3rdparty/jvm/org/scala_lang/modules:scala_xml",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/scala_lang:scala_reflect",
        ":specs2_fp"
    ],
    jars = [
        "//external:jar/org/specs2/specs2_common_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "specs2_core",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/scala_sbt:test_interface",
        ":specs2_matcher"
    ],
    jars = [
        "//external:jar/org/specs2/specs2_core_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "specs2_fp",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library"
    ],
    jars = [
        "//external:jar/org/specs2/specs2_fp_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "specs2_matcher",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":specs2_common"
    ],
    jars = [
        "//external:jar/org/specs2/specs2_matcher_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)


