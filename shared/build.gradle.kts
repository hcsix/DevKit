
plugins {

    id("org.jetbrains.compose")
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
}


dependencies {
}

kotlin {
    jvm()
    sourceSets {
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
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
            }
        }
        jvmMain {
            dependencies{
                implementation(compose.desktop.currentOs)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

    }

}

compose.resources {
    publicResClass = true
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}

