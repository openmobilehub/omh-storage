plugins {
    `android-base-lib`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.omh.android.storage.api"
}

dependencies {
    implementation(Libs.reflection)
    testImplementation(Libs.junit)
}