package com.omh.android.storage.api.domain.usecase

import android.util.Log
import com.omh.android.storage.api.domain.model.OmhFile
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
import java.io.File

@ExperimentalCoroutinesApi
class UploadFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    private val uploadFileUseCase: UploadFileUseCase = UploadFileUseCase(repository, dispatcher)

    private val params: UploadFileUseCaseParams = mockk()

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
    fun `given the params, when UploadFileUseCase success, then result is returned`() = runTest {
        val parentId = "510"
        val file = File("pathFile")
        val expectedResult: OmhFile = mockk()

        every { params.parentId } returns parentId
        every { params.localFileToUpload } returns file

        coEvery { repository.uploadFile(any(), any()) } returns expectedResult

        val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.file, expectedResult)
    }

    @Test
    fun `given the params, when UploadFileUseCase fails, then a OmhError is returned`() = runTest {
        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
