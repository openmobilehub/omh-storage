package com.omh.android.storage.api.drive.nongms.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
internal class FileOrFolderResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("mimeType")
    val mimeType: String,
    @JsonProperty("modifiedTime")
    val modifiedTime: String
)
