object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.androidGradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val detekt by lazy { "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}" }
    val jacoco by lazy { "org.jacoco:org.jacoco.core:${Versions.jacoco}" }
}

object Libs {
    val reflection by lazy { "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}" }

    // KTX
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val lifecycleKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}" }
    val viewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}" }
    val activityKtx by lazy { "androidx.activity:activity-ktx:${Versions.activityKtx}" }

    // Retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitJacksonConverter by lazy { "com.squareup.retrofit2:converter-jackson:${Versions.retrofit}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp}" }
    val okHttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }

    // Coroutines
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }

    // GMS
    val googlePlayServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.googlePlayServicesAuth}" }
    val googleJacksonClient by lazy { "com.google.http-client:google-http-client-jackson:${Versions.googleJacksonClient}" }
    val googleAndroidApiClient by lazy { "com.google.api-client:google-api-client-android:${Versions.googleAndroidApiClient}" }
    val googleDrive by lazy { "com.google.apis:google-api-services-drive:${Versions.googleDriveServices}" }
    val avoidGuavaConflict by lazy { "com.google.guava:listenablefuture:${Versions.avoidGuavaConflict}" }

    // Android
    val androidAppCompat by lazy { "androidx.appcompat:appcompat:${Versions.androidAppCompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }

    // Testing
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val androidJunit by lazy { "androidx.test.ext:junit:${Versions.androidJunit}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val coroutineTesting by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}" }

    // Auth
    val omhNonGmsAuthLibrary by lazy { "com.openmobilehub.android:auth-api-non-gms:${Versions.omhAuth}" }
    val omhGmsAuthLibrary by lazy { "com.openmobilehub.android:auth-api-gms:${Versions.omhAuth}" }

    // Play services
    val googlePlayBase by lazy { "com.google.android.gms:play-services-base:${Versions.googlePlayBase}" }
}
