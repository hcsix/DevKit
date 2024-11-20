
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
                implementation(libs.jna)
                implementation("com.android.tools:sdk-common:31.7.2") {
                    exclude(group = "org.bouncycastle", module = "bcpkix-jdk18on")
                    exclude(group = "org.bouncycastle", module = "bcprov-jdk18on")
                    exclude(group = "org.bouncycastle", module = "bcutil-jdk18on")
                }
                implementation(libs.filekit.core)
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.coroutines)
                implementation(libs.multiplatform.settings.serialization)
                implementation(libs.kotlinx.serialization.json)
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

tasks.create("runJvmMain", JavaExec::class.java) {
    val jars = files().apply {
        from(configurations.getByName("jvmRuntimeClasspath"))
        from(tasks.named("jvmJar"))
    }
    this.setClasspath(jars)
    this.mainClass.set("dev.johnoreilly.confetti.MainKt")
}
