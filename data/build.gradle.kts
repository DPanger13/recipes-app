@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("library-android")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.dpanger.vehicles.data"
}

dependencies {
    implementation(project(":vehicles"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.jna) {
        artifact {
            type = "aar"
        }
    }
}
