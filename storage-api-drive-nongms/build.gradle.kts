plugins {
    `android-base-lib`
}

android {
    namespace = "com.omh.android.storage.api.drive.nongms"
}

dependencies {
    api(project(":storage-api"))

    // Non-GMS
    implementation(Libs.retrofit)
    implementation(Libs.retrofitJacksonConverter)

    // Test dependencies
    testImplementation(Libs.junit)
}