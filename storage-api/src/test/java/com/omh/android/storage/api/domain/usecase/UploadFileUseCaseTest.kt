package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.File

@ExperimentalCoroutinesApi
class UploadFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()

    private val uploadFileUseCase: UploadFileUseCase = UploadFileUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when UploadFileUseCase success, then result is returned`() = runTest {
        val parentId = "510"
        val file = File("pathFile")
        val expectedResult: OmhFile = mockk()
        val params = UploadFileUseCaseParams(file, parentId)

        coEvery { repository.uploadFile(params.localFileToUpload, params.parentId) } returns expectedResult

        val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.file, expectedResult)
    }

    @Test
    fun `given the params, when UploadFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: UploadFileUseCaseParams = mockk()

        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
