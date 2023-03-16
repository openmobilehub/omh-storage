plugins {
    `android-application`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.github.omhstoragedemo"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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