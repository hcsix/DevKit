import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.githubBuildconfig)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
}

dependencies {

    api(project(":shared"))
    implementation(compose.desktop.currentOs)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}



//
//
//dependencies {
//
//    // Local JAR files
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//
//    // Compose Desktop
//    implementation(compose.desktop.currentOs)
//
//    // Coil for image loading
//    implementation("io.coil-kt.coil3:coil-compose:${ext["coilVersion"]}")
//
//    // Ktor for HTTP client
//    implementation("io.ktor:ktor-client-core:${ext["ktorVersion"]}")
//    implementation("io.ktor:ktor-client-cio:${ext["ktorVersion"]}")
//
//    // Compose UI
//    implementation("androidx.compose.ui:ui:${ext["composeUiVersion"]}")
//    implementation("androidx.compose.ui:ui-tooling:${ext["composeUiVersion"]}")
//    implementation("androidx.compose.ui:ui-tooling-preview:${ext["composeUiVersion"]}")
//    implementation("androidx.compose.foundation:foundation:${ext["composeUiVersion"]}")
//    implementation("androidx.compose.material:material:${ext["composeUiVersion"]}")
//    implementation("androidx.compose.material:material-icons-extended-desktop:${ext["composeUiVersion"]}")
//    implementation("androidx.compose.material3:material3:${ext["composeMaterialVersion"]}")
//    implementation("androidx.compose.material3:material3-window-size-class:${ext["composeMaterialVersion"]}")
//    implementation("androidx.compose.material3.adaptive:adaptive:${ext["composeMaterial3AdaptiveVersion"]}")
//    implementation("androidx.compose.material3.adaptive:adaptive-layout:${ext["composeMaterial3AdaptiveVersion"]}")
//    implementation("androidx.compose.material3.adaptive:adaptive-navigation:${ext["composeMaterial3AdaptiveVersion"]}")
//
//    // Lifecycle
//    implementation("androidx.lifecycle:lifecycle-viewmodel-desktop:${ext["lifecycleVersion"]}")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${ext["lifecycleVersion"]}")
//
//    // Multiplatform Settings
//    implementation("com.russhwolf:multiplatform-settings:${ext["multiplatformSettingsVersion"]}")
//    implementation("com.russhwolf:multiplatform-settings-coroutines:${ext["multiplatformSettingsVersion"]}")
//    implementation("com.russhwolf:multiplatform-settings-serialization:${ext["multiplatformSettingsVersion"]}")
//
//    // Kotlin Serialization
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${ext["kotlinxSerializationVersion"]}")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${ext["kotlinxSerializationVersion"]}")
//    implementation("org.jetbrains.kotlinx:kotlinx-datetime:${ext["kotlinxDatetimeVersion"]}")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${ext["kotlinxCoroutinesVersion"]}")
//
//    // APK Signing
//    implementation("com.android.tools.build:apksig:${ext["apksigVersion"]}")
//
//    // APK Builder
//    implementation("com.android.tools.build:gradle:${ext["gradlePluginVersion"]}")
//
//    // Firebase Crashlytics
//    implementation("com.google.firebase:firebase-crashlytics-buildtools:${ext["firebaseCrashlyticsVersion"]}")
//}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ApktoolDesktop"
            packageVersion = "1.0.0"
        }
    }
}