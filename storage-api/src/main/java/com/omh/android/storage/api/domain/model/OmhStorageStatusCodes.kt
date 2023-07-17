/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
