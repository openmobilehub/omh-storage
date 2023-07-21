plugins {
    `android-base-lib`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.omh.android.storage.api"
}

dependencies {
    implementation(Libs.reflection)


    // Omh Auth
    api(Libs.omhNonGmsAuthLibrary)

    // Coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)

    // Play services
    implementation(Libs.googlePlayBase)

    // Test
    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidJunit)
    testImplementation(Libs.mockk)
    testImplementation(Libs.coroutineTesting)
}
