plugins {
    `android-base-lib`
}

android {
    namespace = "com.github.openmobilehub.storage"
}

dependencies {
    // Remove this dummy dependencies and add your own.
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}