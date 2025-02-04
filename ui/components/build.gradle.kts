plugins {
    id("library-android-compose")
}

android {
    namespace = "com.dpanger.vehicles.ui.components"
}

dependencies {
    implementation(project(":ui:themes"))

    implementation(libs.core.ktx)
}
