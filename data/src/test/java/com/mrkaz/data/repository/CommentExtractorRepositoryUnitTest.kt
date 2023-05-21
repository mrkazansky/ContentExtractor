package com.mrkaz.data.repository

import com.mrkaz.data.mapper.ModelMapper
import com.mrkaz.data.model.ExtractedCommentModel
import com.mrkaz.data.repository.implementation.CommentExtractorRepositoryImpl
import com.mrkaz.sdk.main.ContentExtractor
import com.mrkaz.sdk.model.ContentExtractorResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CommentExtractorRepositoryUnitTest {
    @MockK(relaxed = true)
    private lateinit var extractor: ContentExtractor

    @MockK
    private lateinit var mapper: ModelMapper<ContentExtractorResult, ExtractedCommentModel>

    private lateinit var repository: CommentExtractorRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CommentExtractorRepositoryImpl(extractor, mapper)
    }

    @Test
    fun testCommentExtractorRepositoryHappyCase() {
        // Arrange
        val input = "@bill and @billgate"
        val contentExtractorResult =
            ContentExtractorResult(listOf("@bill", "@billgate"), emptyList())
        val expectedExtractedCommentModel =
            ExtractedCommentModel(listOf("@bill", "@billgate"), emptyList())

        coEvery { extractor.extract(input) } returns contentExtractorResult
        coEvery { mapper.map(contentExtractorResult) } returns expectedExtractedCommentModel

        // Assign
        val result = repository.extractContent(input)

        // Assert
        assertEquals(expectedExtractedCommentModel, result)
    }

    @Test
    fun testCommentExtractorRepositoryClose() {
        // Act
        repository.close()

        // Assert
        verify { extractor.close() }
    }
}