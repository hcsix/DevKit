plugins {
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    androidTarget()

    jvm("desktop")

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain {
            dependencies {
                implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.foundation)
                implementation(libs.slf4j.api)
                implementation(libs.slf4j.simple)
                implementation(libs.android.apksig)
                implementation(libs.commons.codec)
                implementation(libs.asm)
                implementation(libs.lifecycle.viewmodel.compose)
                runtimeOnly(libs.kotlinx.coroutines.swing)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.jna)
                implementation("com.android.tools:sdk-common:31.7.2") {
//                    exclude(group = "org.bouncycastle", module = "bcpkix-jdk18on")
//                    exclude(group = "org.bouncycastle", module = "bcprov-jdk18on")
//                    exclude(group = "org.bouncycastle", module = "bcutil-jdk18on")
                }
                implementation(libs.filekit.core)
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.multiplatform.settings.serialization)
                implementation(libs.kotlinx.serialization.json)
//                implementation(libs.moshi)
//                implementation(libs.moshi.kotlin)
                implementation(libs.gson)
                // 依赖注入
                implementation(libs.koin.core)
                // 多模匹配算法aho-corasick
                implementation(libs.aho.corasick.double.array.trie)
                // 解压缩
                implementation(libs.commons.compress)
                // jadx
                implementation(libs.jadx.core)
                implementation(libs.jadx.dex.input)
                implementation(libs.logback.classic)
                // java文件解析
                implementation(libs.javaparser.symbol.solver.core)
                implementation(libs.javaparser.core)
                // xml文件解析
                implementation(libs.dom4j)

                implementation(compose.desktop.currentOs)
            }
        }

        val desktopMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {
                implementation(compose.desktop.common)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

    }

}

android {
    compileSdk = 35
    namespace = "com.supcoder.devkit.common"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

//https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources-usage.html
compose.resources {
    publicResClass = true
}


