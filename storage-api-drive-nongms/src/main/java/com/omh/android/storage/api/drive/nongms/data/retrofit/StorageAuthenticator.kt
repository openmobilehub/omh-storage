/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
