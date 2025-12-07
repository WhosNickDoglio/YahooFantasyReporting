// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.metro)
}

metro { contributesAsInject = true }
