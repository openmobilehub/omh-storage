package com.omh.android.storage.api.drive.nongms.data.source.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class FileRemoteResponse(
    @JsonProperty("mimeType")val mimeType: String?,
    @JsonProperty("id")val id: String?,
    @JsonProperty("name")val name: String?,
    @JsonProperty("modifiedTime")val modifiedTime: String?,
    @JsonProperty("parents")val parents: List<String>?
)
