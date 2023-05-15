package com.omh.android.storage.api

import android.content.Context
import kotlin.reflect.KClass

object OmhStorageProvider {

    private const val NGMS_ADDRESS = "com.omh.android.storage.api.drive.nongms.OmhStorageFactoryImpl"

    @SuppressWarnings("SwallowedException")
    fun provideStorageClient(context: Context): OmhStorageClient {
        val clazz: KClass<out Any> = Class.forName(NGMS_ADDRESS).kotlin
        val omhStorageFactory = clazz.objectInstance as OmhStorageFactory
        return omhStorageFactory.getStorageClient(context)
    }
}
