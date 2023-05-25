package com.mrkaz.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtractedCommentModelUnitTest {

    @Test
    fun testExtractedCommentModelHappyCase() {
        // Arrange
        val mentions = listOf("@bill", "@billgate")
        val links = listOf(
            LinkContent("https://www.genk.vn", "Genk"),
            LinkContent("https://www.google.com", "Google")
        )

        // Assign
        val extractedCommentModel = ExtractedCommentModel(mentions, links)

        // Assert
        assertEquals(mentions, extractedCommentModel.mentions)
        assertEquals(links, extractedCommentModel.links)
    }
}