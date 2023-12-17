@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("library-android")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.dpanger.recipes.data"
}

dependencies {
    implementation(project(":data-multiplatform"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}
