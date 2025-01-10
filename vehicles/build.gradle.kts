plugins {
    id("library-android")
}

android {
    namespace = "com.dpanger.vehicles.uniffi.vehicles"
}

dependencies {
    implementation(libs.jna) {
        artifact {
            type = "aar"
        }
    }
}
