package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.auth.api.OmhCredentials
import com.omh.android.storage.api.drive.nongms.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

internal class GoogleRetrofitImpl(private val omhCredentials: OmhCredentials) {

    companion object {
        private const val HEADER_AUTHORIZATION_NAME = "Authorization"
        private const val BEARER = "Bearer %s"

        private var instance: GoogleRetrofitImpl? = null

        internal fun getInstance(omhCredentials: OmhCredentials): GoogleRetrofitImpl {
            if (instance == null) {
                instance = GoogleRetrofitImpl(omhCredentials)
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
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = setupRequestInterceptor(chain)
                chain.proceed(request)
            }
            .build()
    }

    private fun setupRequestInterceptor(chain: Interceptor.Chain) = chain
        .request()
        .newBuilder()
        .addHeader(
            HEADER_AUTHORIZATION_NAME,
            BEARER.format(omhCredentials.accessToken.orEmpty())
        )
        .build()

    private fun createConverterFactory() = JacksonConverterFactory.create()
}
