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

package com.omh.android.storage.api.async

import com.omh.android.auth.api.async.OmhCancellable
import com.omh.android.auth.api.async.OmhTask
import com.omh.android.storage.api.domain.usecase.OmhResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class OmhStorageTaskImpl<T>(private val task: suspend () -> OmhResult<T>) : OmhTask<T>() {

    private val coroutineContext = Dispatchers.Main + SupervisorJob()
    private val customScope: CoroutineScope = CoroutineScope(context = coroutineContext)

    override fun execute(): OmhCancellable {
        customScope.launch {
            executeScopedTask()
        }
        return OmhCancellable { coroutineContext.cancelChildren() }
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    private suspend fun executeScopedTask() {
        when (val result: OmhResult<T> = task()) {
            is OmhResult.OmhSuccess -> executeSuccess(result.data)
            is OmhResult.OmhError -> executeFailure(result.exception)
        }
    }

    private suspend fun executeFailure(e: Exception) = withContext(Dispatchers.Main) {
        onFailure?.invoke(e).also { e.printStackTrace() }
    }

    private suspend fun executeSuccess(data: T) {
        withContext(Dispatchers.Main) {
            onSuccess?.invoke(data)
        }
    }
}
