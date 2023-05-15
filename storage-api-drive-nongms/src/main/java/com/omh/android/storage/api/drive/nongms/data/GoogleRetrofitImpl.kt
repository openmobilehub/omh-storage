package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

internal object GoogleRetrofitImpl {

    private const val HEADER_AUTHORIZATION_NAME = "Authorization"
    private const val TIMEOUT = 50L

    private var apiService: GoogleStorageApiService? = null

    fun getGoogleStorageApiService(): GoogleStorageApiService {
        apiService?.let { return it }

        val retrofit = Retrofit.Builder()
            .client(createOkHttpClient())
            .addConverterFactory(createConverterFactory())
            .baseUrl(BuildConfig.G_STORAGE_URL)
            .build()

        return retrofit.create(GoogleStorageApiService::class.java).also { apiService = it }
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = setupLoggingInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = setupRequestInterceptor(chain)
                chain.proceed(request)
            }
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun setupLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    private fun setupRequestInterceptor(chain: Interceptor.Chain) = chain
        .request()
        .newBuilder()
        .addHeader(
            HEADER_AUTHORIZATION_NAME,
            // Bearer must come from auth lib
            "Bearer ya29.a0AWY7Ckl29g9Y7..."
        )
        .build()

    private fun createConverterFactory() = JacksonConverterFactory.create()
}
