package com.omh.android.storage.api.domain.usecase

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class OmhSuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    companion object {

        private const val TAG_ERROR = "Error = "
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    suspend operator fun invoke(parameters: P): OmhResult<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    OmhResult.OmhSuccess(it)
                }
            }
        } catch (e: Exception) {
            OmhResult.OmhError(e).also {
                Log.e(TAG_ERROR, it.toString())
            }
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
