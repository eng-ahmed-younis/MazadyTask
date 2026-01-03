// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false // Use the same version as your Kotlin plugin
    alias(libs.plugins.apollo) apply false

    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
}