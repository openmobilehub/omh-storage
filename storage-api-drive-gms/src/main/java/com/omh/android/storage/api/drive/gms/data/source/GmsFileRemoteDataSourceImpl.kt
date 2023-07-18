package com.omh.android.storage.api.drive.gms.data.source

import android.webkit.MimeTypeMap
import com.google.api.client.http.FileContent
import com.google.api.client.http.HttpResponseException
import com.google.api.services.drive.model.FileList
import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.gms.data.GoogleDriveApiService
import com.omh.android.storage.api.drive.gms.data.source.mapper.toOmhFile
import java.io.ByteArrayOutputStream
import java.io.File
import com.google.api.services.drive.model.File as GoogleApiFile

internal class GmsFileRemoteDataSourceImpl(private val apiService: GoogleDriveApiService) :
    OmhFileRemoteDataSource {

    companion object {
        private const val ANY_MIME_TYPE = "*/*"
    }

    override fun getFilesList(parentId: String): List<OmhFile> {
        val googleJsonFileList: FileList = apiService.getFilesList(parentId).execute()
        val googleFileList: List<GoogleApiFile> = googleJsonFileList.files.toList()
        return googleFileList.mapNotNull { googleFile -> googleFile.toOmhFile() }
    }

    override fun createFile(name: String, mimeType: String, parentId: String?): OmhFile? {
        val fileToBeCreated = GoogleApiFile().apply {
            this.name = name
            this.mimeType = mimeType
            if (parentId != null) {
                this.parents = listOf(parentId)
            }
        }

        val responseFile: GoogleApiFile = apiService.createFile(fileToBeCreated).execute()

        return responseFile.toOmhFile()
    }

    @SuppressWarnings("TooGenericExceptionCaught", "SwallowedException")
    override fun deleteFile(fileId: String): Boolean {
        return try {
            apiService.deleteFile(fileId).execute()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun uploadFile(localFileToUpload: File, parentId: String?): OmhFile? {
        val localMimeType = getStringMimeTypeFromLocalFile(localFileToUpload)

        val file = GoogleApiFile().apply {
            name = localFileToUpload.name
            mimeType = localMimeType
            parents = if (parentId.isNullOrBlank()) {
                emptyList()
            } else {
                listOf(parentId)
            }
        }

        val mediaContent = FileContent(localMimeType, localFileToUpload)

        val response: GoogleApiFile = apiService.uploadFile(file, mediaContent).execute()

        return response.toOmhFile()
    }

    private fun getStringMimeTypeFromLocalFile(file: File) = MimeTypeMap
        .getSingleton()
        .getMimeTypeFromExtension(file.extension)
        ?: ANY_MIME_TYPE

    @SuppressWarnings("SwallowedException")
    override fun downloadFile(fileId: String, mimeType: String?): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()

        try {
            apiService.downloadFile(fileId).executeMediaAndDownloadTo(outputStream)
        } catch (exception: HttpResponseException) {
            with(outputStream) {
                flush()
                reset()
            }

            if (!mimeType.isNullOrBlank()) {
                apiService
                    .downloadGoogleDoc(fileId, mimeType)
                    .executeMediaAndDownloadTo(outputStream)
            }
        }

        return outputStream
    }

    override fun updateFile(localFileToUpload: File, fileId: String): OmhFile? {
        val localMimeType = getStringMimeTypeFromLocalFile(localFileToUpload)

        val file = GoogleApiFile().apply {
            name = localFileToUpload.name
            mimeType = localMimeType
        }

        val mediaContent = FileContent(localMimeType, localFileToUpload)

        val response: GoogleApiFile = apiService.updateFile(fileId, file, mediaContent).execute()

        return response.toOmhFile()
    }
}
