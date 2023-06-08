package com.omh.android.storage.api.drive.gms.data.source

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile

class GmsFileRemoteDataSourceImpl : OmhFileRemoteDataSource {

    override fun getFilesList(parentId: String): List<OmhFile> {
        return emptyList()
    }

    override fun createFile(name: String, mimeType: String, parentId: String?): OmhFile? {
        return null
    }

    override fun deleteFile(fileId: String): Boolean {
        return false
    }
}
