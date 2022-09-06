// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.13")  // Google Services plugin
        // Add the Crashlytics Gradle plugin
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
    }
}

task<Delete>("clean"){
    delete(rootProject.buildDir)
}