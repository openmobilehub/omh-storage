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

package com.omh.android.storage.api.data.mapper

import com.omh.android.storage.api.domain.model.OmhFileType

internal object FileTypeMapper {

    private val MAP_FILE_TYPES: Map<String, OmhFileType> =
        OmhFileType.values().associateBy { fileType -> fileType.mimeType }

    fun getFileTypeWithMime(mimeType: String): OmhFileType {
        return if (MAP_FILE_TYPES.containsKey(mimeType)) {
            MAP_FILE_TYPES[mimeType] ?: OmhFileType.OTHER
        } else {
            OmhFileType.OTHER
        }
    }
}
