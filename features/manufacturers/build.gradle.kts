plugins {
    id("library-android-compose")
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
    implementation(libs.lifecycle.viewmodel)

    testImplementation(libs.bundles.testing)
}
