package com.omh.android.storage.api.data.source

import com.omh.android.storage.api.domain.model.OmhFile
import java.io.ByteArrayOutputStream
import java.io.File

interface OmhFileRemoteDataSource {

    fun getFilesList(parentId: String = "root"): List<OmhFile>

    fun createFile(name: String, mimeType: String, parentId: String?): OmhFile?

    /**
     * @return true if the file was deleted, false otherwise
     */
    fun deleteFile(fileId: String): Boolean

    fun uploadFile(localFileToUpload: File, parentId: String?): OmhFile?

    fun downloadFile(fileId: String, mimeType: String?): ByteArrayOutputStream
}
