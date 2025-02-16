plugins {
    id("recipes-application")
}

android {
    namespace = "com.dpanger.vehicles"

    defaultConfig {
        applicationId = "com.dpanger.vehicles"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":data:feature_flags"))
    implementation(project(":features:makes"))
    implementation(project(":features:manufacturers"))
    implementation(project(":features:search"))
    implementation(project(":features:search_results"))
    implementation(project(":ui:themes"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.activity.compose)

    testImplementation(libs.bundles.testing)
}
