package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

internal object GoogleRetrofitImpl {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        )
        .build()

    private val retrofitClient = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.G_STORAGE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    val googleStorageREST: GoogleStorageREST = retrofitClient.create(GoogleStorageREST::class.java)
}
