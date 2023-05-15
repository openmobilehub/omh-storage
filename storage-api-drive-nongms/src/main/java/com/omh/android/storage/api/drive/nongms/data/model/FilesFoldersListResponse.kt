package com.omh.android.storage.api.drive.nongms.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
internal class FilesFoldersListResponse(
    @JsonProperty("files")
    val files: List<FileOrFolderResponse>
)
