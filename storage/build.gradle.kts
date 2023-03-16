plugins {
    `android-base-lib`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.github.openmobilehub.storage"
}

dependencies {
    testImplementation(Libs.junit)
}