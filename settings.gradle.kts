// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

rootProject.name = "YahooFantasyReporting"

pluginManagement {
    repositories {
        exclusiveContent {
            forRepository { google() }
            filter {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroup("com.google.testing.platform")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        exclusiveContent {
            forRepository { google() }
            filter {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroup("com.google.testing.platform")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "4.5.0"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.8.0"
}

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":data-collector",
    ":kover-aggregate",
    ":simple-logger",
    ":league-info",
    ":metro-annotations",
    ":yahoo-data-fetcher",
    ":google-sheets-api",
    ":roster-evaluator",
    ":data-reporter",
    ":discord-api",
)
