plugins {
    `android-base-lib`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.omh.android.storage.api.drive.gms"

    lint {
        disable.add("DuplicatePlatformClasses")
    }

    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        resources.excludes.add("/META-INF/DEPENDENCIES")
        resources.excludes.add("/META-INF/LICENSE")
        resources.excludes.add("/META-INF/LICENSE.txt")
        resources.excludes.add("/META-INF/license.txt")
        resources.excludes.add("/META-INF/NOTICE")
        resources.excludes.add("/META-INF/NOTICE.txt")
        resources.excludes.add("/META-INF/notice.txt")
        resources.excludes.add("/META-INF/ASL2.0")
    }
}

dependencies {
    api("com.openmobilehub.android:storage-api:1.0.3-rc")

    // GMS
    implementation(Libs.googlePlayServicesAuth)
    implementation(Libs.googleJacksonClient)
    implementation(Libs.googleAndroidApiClient) {
        exclude("org.apache.httpcomponents")
    }
    implementation(Libs.googleDrive) {
        exclude("org.apache.httpcomponents")
    }
    implementation(Libs.avoidGuavaConflict)

    // Test dependencies
    testImplementation(Libs.junit)
    testImplementation(Libs.mockk)
}