package com.omh.android.storage.api.drive.nongms.data.mapper

import com.omh.android.storage.api.data.model.RemoteFile
import com.omh.android.storage.api.data.model.RemoteFileOrFolder
import com.omh.android.storage.api.data.model.RemoteFolder
import com.omh.android.storage.api.drive.nongms.data.model.FilesFoldersListResponse
import retrofit2.Response
import java.text.SimpleDateFormat

private const val RESPONSE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

internal fun fromFilesFoldersResponseListToData(
    response: Response<FilesFoldersListResponse>
): List<RemoteFileOrFolder> {
    val outputList = mutableListOf<RemoteFileOrFolder>()
    val filesInBody = response.body()?.files
    if (!response.isSuccessful || filesInBody == null) return outputList
    filesInBody.map { fileFolderResponse ->
        val modifiedTimeAsLong = SimpleDateFormat(RESPONSE_DATE_FORMAT)
            .parse(fileFolderResponse.modifiedTime)
            .time
        outputList.add(
            if (fileFolderResponse.mimeType.contains("folder")) {
                RemoteFolder(
                    fileFolderResponse.name,
                    modifiedTimeAsLong
                )
            } else {
                RemoteFile(
                    fileFolderResponse.name,
                    fileFolderResponse.mimeType,
                    modifiedTimeAsLong
                )
            }
        )
    }
    return outputList
}
