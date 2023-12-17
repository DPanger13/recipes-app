@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("recipes-application")
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.dpanger.recipes"

    defaultConfig {
        applicationId = "com.dpanger.recipes"
        versionCode = 1
        versionName = "0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":themes"))
    implementation(project(":home"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.activity.compose)
    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
}
