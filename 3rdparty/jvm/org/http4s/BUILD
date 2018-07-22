load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary", "scala_test")
scala_import(
    name = "blaze_core",
    exports = [
        "//3rdparty/jvm/org/log4s:log4s",
        "//3rdparty/jvm/org/scala_lang:scala_library"
    ],
    jars = [
        "//external:jar/org/http4s/blaze_core_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "blaze_http",
    exports = [
        "//3rdparty/jvm/com/twitter:hpack",
        "//3rdparty/jvm/org/eclipse/jetty/alpn:alpn_api",
        "//3rdparty/jvm/org/log4s:log4s",
        "//3rdparty/jvm/org/scala_lang/modules:scala_xml",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":blaze_core",
        ":http4s_websocket"
    ],
    jars = [
        "//external:jar/org/http4s/blaze_http_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "http4s_blaze_client",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":http4s_blaze_core",
        ":http4s_client"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_blaze_client_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)



scala_import(
    name = "http4s_blaze_core",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":blaze_http",
        ":http4s_core"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_blaze_core_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "http4s_blaze_server",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":http4s_blaze_core",
        ":http4s_server"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_blaze_server_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)



scala_import(
    name = "http4s_client",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":http4s_core"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_client_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "http4s_core",
    exports = [
        "//3rdparty/jvm/co/fs2:fs2_io",
        "//3rdparty/jvm/co/fs2:fs2_scodec",
        "//3rdparty/jvm/org/log4s:log4s",
        "//3rdparty/jvm/org/scala_lang:scala_library",
        "//3rdparty/jvm/org/scodec:scodec_bits",
        "//3rdparty/jvm/org/typelevel:cats_core",
        "//3rdparty/jvm/org/typelevel:cats_effect",
        ":http4s_websocket",
        ":parboiled"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_core_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "http4s_dsl",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":http4s_core"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_dsl_2_11"
    ],
    visibility = [
        "//visibility:public"
    ]
)



scala_import(
    name = "http4s_server",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library",
        ":http4s_core"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_server_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "http4s_websocket",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library"
    ],
    jars = [
        "//external:jar/org/http4s/http4s_websocket_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



scala_import(
    name = "parboiled",
    exports = [
        "//3rdparty/jvm/org/scala_lang:scala_library"
    ],
    jars = [
        "//external:jar/org/http4s/parboiled_2_11"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)


