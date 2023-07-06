package com.omh.android.storage.api.drive.nongms.data.retrofit

import com.omh.android.auth.api.OmhCredentials
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class StorageAuthenticator(private val credentials: OmhCredentials) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshedToken = credentials.blockingRefreshToken() ?: return null
        return response.request
            .newBuilder()
            .header(
                name = GoogleRetrofitImpl.HEADER_AUTHORIZATION_NAME,
                value = GoogleRetrofitImpl.BEARER.format(refreshedToken)
            )
            .build()
    }
}
