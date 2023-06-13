package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object OmhStorageProvider {

    private const val NGMS_ADDRESS = "com.omh.android.storage.api.drive.nongms.OmhNonGmsStorageFactoryImpl"

    fun provideStorageClient(authClient: OmhAuthClient): OmhStorageClient {
        val clazz: Class<*> = Class.forName(NGMS_ADDRESS)
        val kClass: KClass<*> = clazz.kotlin
        val instance: Any = kClass.createInstance()

        val storageFactory = instance as OmhStorageFactory

        return storageFactory.getStorageClient(authClient)
    }
}
