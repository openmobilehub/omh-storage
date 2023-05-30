package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object OmhStorageProvider {

    private const val NGMS_ADDRESS = "com.omh.android.storage.api.drive.nongms.OmhStorageFactoryImpl"

    @SuppressWarnings("ThrowingExceptionsWithoutMessageOrCause")
    fun provideStorageClient(authClient: OmhAuthClient): OmhStorageClient {
        val clazz: Class<*> = Class.forName(NGMS_ADDRESS)
        val kClass: KClass<*> = clazz.kotlin
        val instance: Any = kClass.createInstance()

        val storageFactory = instance as? OmhStorageFactory ?: throw NullPointerException()

        return storageFactory.getStorageClient(authClient)
    }
}
