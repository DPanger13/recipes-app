plugins {
    id("library-android-compose")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.dpanger.vehicles.features.manufacturers"

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":ui:components"))
    implementation(project(":ui:themes"))
    implementation(project(":data:vehicles"))

    implementation(libs.core.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.lifecycle.viewmodel)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.bundles.testing)
}

kapt {
    correctErrorTypes = true
}
