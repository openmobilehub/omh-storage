plugins {
    `android-base-lib`
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.github.openmobilehub.storage"

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
    // GMS
    implementation("com.google.android.gms:play-services-auth:16.0.1")
    implementation("com.google.http-client:google-http-client-gson:1.26.0")
    implementation("com.google.api-client:google-api-client-android:1.26.0") {
        exclude("org.apache.httpcomponents")
    }
    implementation("com.google.apis:google-api-services-drive:v3-rev136-1.25.0") {
        exclude("org.apache.httpcomponents")
    }
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")

    // Non-GMS
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}