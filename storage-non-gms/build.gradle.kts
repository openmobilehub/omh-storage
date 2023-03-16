plugins {
    `android-base-lib`
}

android {
    namespace = "com.github.openmobilehub.storage.nongms"
}

dependencies {
    api(project(":storage"))

    // Non-GMS
    implementation(Libs.retrofit)
    implementation(Libs.retrofitJacksonConverter)

    // Test dependencies
    testImplementation(Libs.junit)
}