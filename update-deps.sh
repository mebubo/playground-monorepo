#!/bin/bash

THIS_DIR=$(readlink -f .)

(
    cd ../bazel-deps
    bazel run //:parse -- generate -r $THIS_DIR -s 3rdparty/workspace.bzl -d dependencies.yaml
)
