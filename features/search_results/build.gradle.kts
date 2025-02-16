plugins {
    id("library-android-compose")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.dpanger.vehicles.features.searchresults"

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":data:vehicles"))
    implementation(project(":ui:components"))
    implementation(project(":ui:themes"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.serialization)

    testImplementation(libs.bundles.testing)
}
