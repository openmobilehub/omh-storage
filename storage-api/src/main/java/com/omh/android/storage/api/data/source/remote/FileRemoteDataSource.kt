package com.omh.android.storage.api.data.source.remote

import com.omh.android.storage.api.domain.model.File

interface FileRemoteDataSource {

    fun getRootFilesList(): List<File>
}
