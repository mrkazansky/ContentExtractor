package com.mrkaz.domain.usecase

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mrkaz.data.model.ExtractedCommentModel
import com.mrkaz.data.model.LinkContent
import com.mrkaz.data.repository.CommentExtractorRepository
import com.mrkaz.domain.usecase.implementation.ExtractContentUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExtractedCommentUseCaseUnitTest {
    @MockK(relaxed = true)
    private lateinit var repository: CommentExtractorRepository

    private lateinit var gson: Gson

    private lateinit var useCase: ExtractCommentUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gson = GsonBuilder().create()
        useCase = ExtractContentUseCaseImpl(repository, gson)
    }

    @Test
    fun testExtractedCommentUseCaseHappyCase() = runBlocking {
        // Arrange
        val input = "@bill and @billgate read https://www.genk.vn and https://www.google.com"
        val extractedContent = ExtractedCommentModel(
            listOf("@bill", "@billgate"),
            listOf(
                LinkContent("https://www.genk.vn", "Genk"),
                LinkContent("https://www.google.com", "Google")
            )
        )
        val expectedJson =
            "{\"mentions\":[\"@bill\",\"@billgate\"],\"links\":[{\"url\":\"https://www.genk.vn\",\"title\":\"Genk\"},{\"url\":\"https://www.google.com\",\"title\":\"Google\"}]}"

        every { repository.extractContent(input) } returns extractedContent

        // Assign
        val result = useCase.extractComment(input)

        // Assert
        assertEquals(expectedJson, result)
    }

    @Test
    fun testExtractedCommentUseCaseEmptyCase() = runBlocking {
        // Arrange
        val input = "bill and billgate"
        val extractedContent = ExtractedCommentModel(emptyList(), emptyList())
        val expectedJson = ""

        every { repository.extractContent(input) } returns extractedContent

        // Assign
        val result = useCase.extractComment(input)

        // Assert
        assertEquals(expectedJson, result)
    }

    @Test
    fun testExtractedCommentUseCaseCloseCase() {
        // Act
        useCase.close()

        // Assert
        verify { repository.close() }
    }
}