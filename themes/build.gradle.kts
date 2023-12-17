@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("library-android-compose")
}

android {
    namespace = "com.dpanger.recipes.themes"
}

dependencies {
    implementation(libs.core.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)
}
