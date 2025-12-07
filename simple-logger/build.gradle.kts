// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.metro)
}

metro { contributesAsInject = true }

dependencies {
    // https://www.slf4j.org/codes.html#noProviders
    runtimeOnly(libs.slf4j)
}
