plugins {
    id("library-android-compose")
}

android {
    namespace = "com.dpanger.vehicles.features.search"

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

    testImplementation(libs.bundles.testing)
}
