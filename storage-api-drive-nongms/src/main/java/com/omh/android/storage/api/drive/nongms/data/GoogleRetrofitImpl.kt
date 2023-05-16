package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

internal class GoogleRetrofitImpl(private val bearerToken: String) {

    companion object {
        private const val HEADER_AUTHORIZATION_NAME = "Authorization"

        private var instance: GoogleRetrofitImpl? = null

        internal fun getInstance(bearerToken: String): GoogleRetrofitImpl {
            if (instance == null) {
                instance = GoogleRetrofitImpl(bearerToken)
            }

            return instance!!
        }
    }

    private var apiService: GoogleStorageApiService? = null

    fun getGoogleStorageApiService(): GoogleStorageApiService {
        if (apiService != null) {
            return apiService!!
        }

        val retrofit = Retrofit.Builder()
            .client(createOkHttpClient())
            .addConverterFactory(createConverterFactory())
            .baseUrl(BuildConfig.G_STORAGE_URL)
            .build()

        apiService = retrofit.create(GoogleStorageApiService::class.java)
        return apiService!!
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = setupLoggingInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = setupRequestInterceptor(chain)
                chain.proceed(request)
            }
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
            bearerToken
        )
        .build()

    private fun createConverterFactory() = JacksonConverterFactory.create()
}
