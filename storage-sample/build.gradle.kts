import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    `android-application`
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.44" apply true
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.omh.android.storage.sample"

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "CLIENT_ID",
            value = gradleLocalProperties(rootDir)["clientId"].toString()
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    flavorDimensions += "google_services"
    productFlavors {
        create("ngms") {
            dimension = "google_services"
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }

    kapt {
        correctErrorTypes = true
    }
}

val ngmsImplementation by configurations

dependencies {
    implementation(project(":storage-api"))

    // Omh Auth
    ngmsImplementation(Libs.omhNonGmsAuthLibrary)
    ngmsImplementation(Libs.omhGmsAuthLibrary)
    ngmsImplementation(project(":storage-api-drive-nongms"))
    ngmsImplementation(project(":storage-api-drive-gms"))

    implementation(Libs.coreKtx)
    implementation(Libs.lifecycleKtx)
    implementation(Libs.viewModelKtx)
    implementation(Libs.activityKtx)
    implementation(Libs.androidAppCompat)
    implementation(Libs.material)
    implementation(Libs.coroutinesAndroid)
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    testImplementation(Libs.junit)
}