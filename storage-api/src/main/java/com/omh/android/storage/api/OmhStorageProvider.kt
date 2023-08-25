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

package com.omh.android.storage.api

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.models.OmhAuthStatusCodes
import com.omh.android.storage.api.domain.model.OmhStorageException
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class OmhStorageProvider private constructor(
    private val gmsPath: String?,
    private val nonGmsPath: String?
) {

    class Builder {

        companion object {

            private const val NON_GMS_ADDRESS =
                "com.omh.android.storage.api.drive.nongms.OmhNonGmsStorageFactoryImpl"

            private const val GMS_ADDRESS =
                "com.omh.android.storage.api.drive.gms.OmhGmsStorageFactoryImpl"
        }

        private var gmsPath: String? = null
        private var nonGmsPath: String? = null

        @JvmOverloads
        fun addGmsPath(path: String? = GMS_ADDRESS): Builder {
            gmsPath = path
            return this
        }

        @JvmOverloads
        fun addNonGmsPath(path: String? = NON_GMS_ADDRESS): Builder {
            nonGmsPath = path
            return this
        }

        fun build(): OmhStorageProvider = OmhStorageProvider(gmsPath, nonGmsPath)
    }

    private val isSingleBuild = gmsPath != null && nonGmsPath != null

    @SuppressWarnings("SwallowedException")
    fun provideStorageClient(authClient: OmhAuthClient, context: Context): OmhStorageClient {
        val storageFactory: OmhStorageFactory = try {
            getOmhStorageFactory(context)
        } catch (exception: ClassNotFoundException) {
            throw OmhStorageException.ApiException(OmhAuthStatusCodes.DEVELOPER_ERROR, exception)
        }

        return storageFactory.getStorageClient(authClient)
    }

    private fun getOmhStorageFactory(context: Context): OmhStorageFactory = when {
        isSingleBuild -> reflectSingleBuild(context)
        gmsPath != null -> getFactoryImplementation(gmsPath)
        nonGmsPath != null -> getFactoryImplementation(nonGmsPath)
        else -> throw OmhStorageException.ApiException(OmhAuthStatusCodes.DEVELOPER_ERROR)
    }

    private fun reflectSingleBuild(context: Context): OmhStorageFactory {
        val path = if (hasGoogleServices(context)) {
            gmsPath!!
        } else {
            nonGmsPath!!
        }
        return getFactoryImplementation(path)
    }

    private fun getFactoryImplementation(path: String): OmhStorageFactory {
        val clazz: Class<*> = Class.forName(path)
        val kClass: KClass<*> = clazz.kotlin
        val instance: Any = kClass.createInstance()

        return instance as OmhStorageFactory
    }

    private fun hasGoogleServices(context: Context): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val playServicesAvailable: Int = googleApiAvailability
            .isGooglePlayServicesAvailable(context)
        return playServicesAvailable == ConnectionResult.SUCCESS
    }
}
