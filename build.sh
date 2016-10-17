#!/bin/bash

if [ -n "${BUILD_NUMBER:+1}" ]; then
    echo 'Build Number: '${BUILD_NUMBER}
    VERSION_FILE='./build.gradle'
    chmod 644 ${VERSION_FILE}
    sed -i "s|build_number =\(.*\)|build_number = ${BUILD_NUMBER}|g"  ${VERSION_FILE}
else
    echo 'Build Number: not set'
fi


./gradlew :sdl_android_lib:build
