plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.application")
    id("org.jetbrains.compose")
}


kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                api(project(":shared"))
                implementation(libs.appcompat)
            }
        }
    }
}

android {
    compileSdk = 35
    namespace = "com.supcoder.devkit"
    defaultConfig {
        applicationId = "com.supcoder.devkit"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
