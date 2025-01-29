plugins {
    id("library-android")
    kotlin("kapt")
}

android {
    namespace = "com.dpanger.vehicles.featureflags"
}

dependencies {
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    testImplementation(libs.bundles.testing)
}
