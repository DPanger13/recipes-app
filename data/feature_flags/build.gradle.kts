plugins {
    id("library-android")
}

android {
    namespace = "com.dpanger.vehicles.featureflags"
}

dependencies {
    testImplementation(libs.bundles.testing)
}
