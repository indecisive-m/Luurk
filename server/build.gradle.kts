plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.koin.compiler)


}

group = "com.example.luurk"
version = "1.0.0"
application {
    mainClass = "com.example.luurk.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)
    implementation(libs.bundles.exposed)
    implementation(libs.koin.annotations)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)

}