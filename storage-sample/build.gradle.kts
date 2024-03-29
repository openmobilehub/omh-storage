import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    `android-application`
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.44" apply true
    id("com.openmobilehub.android.omh-core")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

omhConfig {
    bundle("singleBuild") {
        storage() {
            gmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-gms:1.0.7-beta"
            }
            nonGmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-nongms:1.0.8-beta"
            }
        }
        auth {
            gmsService {
                dependency = "com.openmobilehub.android:auth-api-gms:1.0.1-beta"
            }
            nonGmsService {
                dependency = "com.openmobilehub.android:auth-api-non-gms:1.0.1-beta"
            }
        }
    }
    bundle("gms") {
        storage {
            gmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-gms:1.0.7-beta"
            }
        }
        auth {
            gmsService {
                dependency = "com.openmobilehub.android:auth-api-gms:1.0.1-beta"
            }
        }
    }
    bundle("nongms") {
        storage {
            nonGmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-nongms:1.0.8-beta"
            }
        }
        auth {
            nonGmsService {
                dependency = "com.openmobilehub.android:auth-api-non-gms:1.0.1-beta"
            }
        }
    }
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.omh.android.storage.sample"

    signingConfigs {
        create("release") {
            val localProperties = gradleLocalProperties(rootDir)
            storeFile = file(localProperties["keypath"].toString())
            storePassword = localProperties["keypass"].toString()
            keyAlias = localProperties["keyalias"].toString()
            keyPassword = localProperties["keypassword"].toString()
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
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

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(Libs.coreKtx)
    implementation(Libs.lifecycleKtx)
    implementation(Libs.viewModelKtx)
    implementation(Libs.activityKtx)
    implementation(Libs.fragmentKtx)
    implementation(Libs.androidAppCompat)
    implementation(Libs.material)
    implementation(Libs.coroutinesAndroid)
    implementation(Libs.splash)
    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUIKtx)
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    testImplementation(Libs.junit)
}