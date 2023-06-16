package com.omh.android.storage.sample.di

import android.content.Context
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.OmhAuthProvider
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.OmhStorageProvider
import com.omh.android.storage.sample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    fun providesOmhAuthClient(@ApplicationContext context: Context): OmhAuthClient {
        return OmhAuthProvider.Builder()
            .addNonGmsPath()
            .addGmsPath()
            .build()
            .provideAuthClient(
                context = context,
                scopes = listOf(
                    "openid",
                    "email",
                    "profile",
                    "https://www.googleapis.com/auth/drive",
                    "https://www.googleapis.com/auth/drive.file"
                ),
                clientId = BuildConfig.CLIENT_ID
            )
    }

    @Provides
    fun providesOmhStorageClient(
        omhAuthClient: OmhAuthClient,
        @ApplicationContext context: Context
    ): OmhStorageClient {
        return OmhStorageProvider.provideStorageClient(omhAuthClient, context)
    }
}
