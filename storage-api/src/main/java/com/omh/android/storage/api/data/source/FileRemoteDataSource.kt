package com.omh.android.storage.api.data.source

import com.omh.android.storage.api.domain.model.File

interface FileRemoteDataSource {

    fun getRootFilesList(): List<File>
}
