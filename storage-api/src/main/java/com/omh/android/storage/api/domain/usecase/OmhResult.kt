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

package com.omh.android.storage.api.domain.usecase

sealed class OmhResult<out R> {

    companion object {
        private const val TAG_SUCCESS = "[RESULT SUCCESS -> data: %s]"
        private const val TAG_ERROR = "[RESULT ERROR -> exception: %s]"
    }

    data class OmhSuccess<out T>(val data: T) : OmhResult<T>()

    data class OmhError(val exception: Exception) : OmhResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is OmhSuccess<*> -> String.format(TAG_SUCCESS, data.toString())
            is OmhError -> String.format(TAG_ERROR, exception.toString())
        }
    }
}

object NoParams

object NoResult
