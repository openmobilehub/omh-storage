package com.omh.android.storage.api

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.omh.android.auth.api.OmhAuthClient
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object OmhStorageProvider {

    private const val NON_GMS_ADDRESS = "com.omh.android.storage.api.drive.nongms.OmhNonGmsStorageFactoryImpl"
    private const val GMS_ADDRESS = "com.omh.android.storage.api.drive.gms.OmhGmsStorageClientImpl"

    fun provideStorageClient(authClient: OmhAuthClient, context: Context): OmhStorageClient {
        val isGms = hasGoogleServices(context)
        val storageFactory = getFactoryImplementation(isGms)
        return storageFactory.getStorageClient(authClient)
    }

    private fun getFactoryImplementation(isGms: Boolean): OmhStorageFactory {
        val address = if (isGms) {
            GMS_ADDRESS
        } else {
            NON_GMS_ADDRESS
        }

        val clazz: Class<*> = Class.forName(address)
        val kClass: KClass<*> = clazz.kotlin
        val instance: Any = kClass.createInstance()

        return instance as OmhStorageFactory
    }

    private fun hasGoogleServices(context: Context): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val playServicesAvailable: Int = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return playServicesAvailable == ConnectionResult.SUCCESS
    }
}
