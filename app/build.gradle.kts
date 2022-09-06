import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val prop = loadProperties(rootProject.file("config/app.properties").path)

fun signingConfig(name: String, propFile: File) = android.signingConfigs.create(name) {
    val prop = loadProperties(propFile.absolutePath)
    storeFile(propFile.parentFile.resolve(prop.getProperty("storeFile")))
    storePassword(prop.getProperty("storePassword"))
    keyAlias(prop.getProperty("keyAlias"))
    keyPassword(prop.getProperty("keyPassword"))
}

val releaseSign = signingConfig("release", rootProject.file("config/key.properties"))

android {
    compileSdk = 32

    defaultConfig {
        applicationId = prop.getProperty("packageName")
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        addManifestPlaceholders(mapOf("ADMOB_APPLICATION_ID" to prop.getProperty("admobId")))
        run {
            resValue("string", "facebook_app_id", prop.getProperty("facebookId"))
            resValue("string", "fb_login_protocol_scheme", "fb${prop.getProperty("facebookId")}")
            resValue("string", "facebook_client_token", prop.getProperty("facebookToken"))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = releaseSign
        }
        debug {
            signingConfig = releaseSign
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
    implementation("com.google.android.gms:play-services-ads:21.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    // facebook
    implementation("com.facebook.android:facebook-android-sdk:latest.release")
    // firebase
    implementation(platform("com.google.firebase:firebase-bom:30.3.1"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
}