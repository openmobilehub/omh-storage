package com.omh.android.storage.api.domain.model

object OmhStorageStatusCodes {

    const val DOWNLOAD_ERROR = 1
    const val DOWNLOAD_GOOGLE_WORKSPACE_ERROR = 2
    const val UPDATE_META_DATA = 3
    const val UPDATE_CONTENT_FILE = 4

    @JvmStatic
    fun getStatusCodeString(code: Int): String {
        return when (code) {
            DOWNLOAD_ERROR -> "Download error."
            DOWNLOAD_GOOGLE_WORKSPACE_ERROR -> "Download Google Workspace error."
            UPDATE_META_DATA -> "Update meta data error."
            UPDATE_CONTENT_FILE -> "Update content file error."
            else -> "Unknown status code: $code"
        }
    }
}
