pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Vehicles-Android"
include(":apps:mobile")
include(":features:makes")
include(":features:manufacturers")
include(":ui:components")
include(":ui:themes")
include(":data:vehicles")
