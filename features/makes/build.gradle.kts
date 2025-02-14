plugins {
    id("library-android-compose")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.dpanger.vehicles.features.makes"

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
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.serialization)

    testImplementation(libs.bundles.testing)
}
