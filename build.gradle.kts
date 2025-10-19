// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

buildscript {
    dependencies {
        classpath(libs.burst)
    }
}

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.metro) apply false
}

// https://docs.gradle.org/8.9/userguide/gradle_daemon.html#daemon_jvm_criteria
tasks.updateDaemonJvm.configure {
    languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
    vendor = JvmVendorSpec.AZUL
}
