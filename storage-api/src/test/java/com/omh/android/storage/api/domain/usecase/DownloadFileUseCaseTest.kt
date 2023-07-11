package com.omh.android.storage.api.domain.usecase

import android.util.Log
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream

@ExperimentalCoroutinesApi
class DownloadFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    private val downloadFileUseCase: DownloadFileUseCase = DownloadFileUseCase(repository, dispatcher)

    private val params: DownloadFileUseCaseParams = mockk()

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
    fun `given the params, when DownloadFileUseCase success, then result is returned`() = runTest {
        val id = "123"
        val mimeType = "image/jpg"
        val expectedResult: ByteArrayOutputStream = mockk()

        every { params.fileId } returns id
        every { params.mimeType } returns mimeType

        coEvery { repository.downloadFile(any(), any()) } returns expectedResult

        val result: OmhResult<DownloadFileUseCaseResult> = downloadFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.outputStream, expectedResult)
    }

    @Test
    fun `given the params, when DownloadFileUseCase fails, then a OmhError is returned`() = runTest {
        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<DownloadFileUseCaseResult> = downloadFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
