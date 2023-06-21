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
        onFailure?.invoke(e)
    }

    private suspend fun executeSuccess(data: T) {
        withContext(Dispatchers.Main) {
            onSuccess?.invoke(data)
        }
    }
}
