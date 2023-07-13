plugins {
    `android-base-lib`
}

android {
    namespace = "com.omh.android.storage.api.drive.nongms"

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "G_STORAGE_URL",
            value = getPropertyOrFail("googleStorageUrl")
        )
    }
}

dependencies {
    api(project(":storage-api"))

    // Retrofit setup
    implementation(Libs.retrofit)
    implementation(Libs.retrofitJacksonConverter)
    implementation(Libs.okHttp)
    implementation(Libs.okHttpLoggingInterceptor)

    // Test dependencies
    testImplementation(Libs.junit)
    testImplementation(Libs.mockk)
}