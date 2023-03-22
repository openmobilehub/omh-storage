plugins {
    `android-application`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.github.omhstoragedemo"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":storage"))

    implementation(Libs.coreKtx)
    implementation(Libs.androidAppCompat)
    implementation(Libs.material)
    testImplementation(Libs.junit)
}