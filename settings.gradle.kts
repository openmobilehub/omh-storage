pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "omh-storage"
include(":storage-api")
include(":storage-api-drive-gms")
include(":storage-api-drive-nongms")
// Sample app is currently broken. Removed temporally
//include(":storage-sample")
