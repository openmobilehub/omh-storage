package com.omh.android.storage.api.data.source

import com.omh.android.storage.api.domain.model.OmhFile

interface OmhFileRemoteDataSource {

    fun getFilesList(parentId: String = "root"): List<OmhFile>

    fun createFile(name: String, mimeType: String, parentId: String?): OmhFile?

    /**
     * @return true if the file was deleted, false otherwise
     */
    fun deleteFile(fileId: String): Boolean
}
