package com.omh.android.storage.api.domain.usecase

import android.util.Log
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()

    private val deleteFileUseCase: DeleteFileUseCase = DeleteFileUseCase(repository)

    @Before
    fun prepareEnvironment() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when DeleteFileUseCase success, then OmhSuccess is returned`() = runTest {
        val id = "123"
        val params = DeleteFileUseCaseParams(id)

        coEvery { repository.deleteFile(params.fileId) } returns true

        val result: OmhResult<DeleteFileUseCaseResult> = deleteFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertTrue((result as OmhResult.OmhSuccess).data.isSuccess)
    }

    @Test
    fun `given the params, when DeleteFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: DeleteFileUseCaseParams = mockk()

        coEvery { repository.deleteFile(any()) } throws RuntimeException()

        val result: OmhResult<DeleteFileUseCaseResult> = deleteFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
