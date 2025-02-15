plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.compose.compiler.gradle)
    implementation(libs.hilt.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.ksp.gradle)
    implementation(libs.ktlint.gradle)
    implementation(libs.serialization.gradle)
}
