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


compose.desktop {
    application {
//        mainClass = "com.supcoder.devkit.MainKt"
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "DevKit"
            packageVersion = "1.0.0"
        }
    }
}