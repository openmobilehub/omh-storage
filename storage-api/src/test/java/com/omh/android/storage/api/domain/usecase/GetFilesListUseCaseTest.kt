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

@ExperimentalCoroutinesApi
class GetFilesListUseCaseTest {
    private val repository: OmhFileRepository = mockk()

    private val getFilesListUseCase: GetFilesListUseCase = GetFilesListUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when GetFilesListUseCase success, then result is returned`() = runTest {
        val expectedResult: List<OmhFile> = mockk()
        val params = GetFilesListUseCaseParams()

        coEvery { repository.getFilesList(params.parentId) } returns expectedResult

        val result: OmhResult<GetFilesListUseCaseResult> = getFilesListUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.files, expectedResult)
    }

    @Test
    fun `given the params, when GetFilesListUseCase fails, then a OmhError is returned`() = runTest {
        val params: GetFilesListUseCaseParams = mockk()

        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<GetFilesListUseCaseResult> = getFilesListUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
