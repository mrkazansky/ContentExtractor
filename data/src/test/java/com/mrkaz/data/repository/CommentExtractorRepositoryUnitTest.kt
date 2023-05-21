package com.mrkaz.data.repository

import com.mrkaz.data.mapper.ModelMapper
import com.mrkaz.data.model.ExtractedCommentModel
import com.mrkaz.data.repository.implementation.CommentExtractorRepositoryImpl
import com.mrkaz.sdk.main.ContentExtractor
import com.mrkaz.sdk.model.ContentExtractorResult
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CommentExtractorRepositoryUnitTest {

    @Mock
    private lateinit var extractor: ContentExtractor

    @Mock
    private lateinit var mapper: ModelMapper<ContentExtractorResult, ExtractedCommentModel>

    private lateinit var repository: CommentExtractorRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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

        `when`(extractor.extract(input)).thenReturn(contentExtractorResult)
        `when`(mapper.map(contentExtractorResult)).thenReturn(expectedExtractedCommentModel)

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
        verify(extractor, times(1)).close()
    }
}