package com.omh.android.storage.api.drive.nongms.data.source.response

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
internal data class FileListRemoteResponse(
    @JsonProperty("files") val files: List<FileRemoteResponse?>?
)
