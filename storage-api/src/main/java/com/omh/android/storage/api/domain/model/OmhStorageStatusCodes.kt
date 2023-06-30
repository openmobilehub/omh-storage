package com.omh.android.storage.api.domain.model

object OmhStorageStatusCodes {

    const val DOWNLOAD_ERROR = 1
    const val DOWNLOAD_GOOGLE_WORKSPACE_ERROR = 2

    @JvmStatic
    fun getStatusCodeString(code: Int): String {
        return when (code) {
            DOWNLOAD_ERROR -> "Download error."
            DOWNLOAD_GOOGLE_WORKSPACE_ERROR -> "Download Google Workspace error."
            else -> "Unknown status code: $code"
        }
    }
}
