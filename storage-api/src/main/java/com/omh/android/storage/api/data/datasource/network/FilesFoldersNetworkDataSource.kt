package com.omh.android.storage.api.data.datasource.network

import com.omh.android.storage.api.data.model.RemoteFileOrFolder

interface FilesFoldersNetworkDataSource {
    fun getAll(): List<RemoteFileOrFolder>
}
