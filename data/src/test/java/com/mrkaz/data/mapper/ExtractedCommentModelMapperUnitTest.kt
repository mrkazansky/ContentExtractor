package com.mrkaz.data.mapper

import com.mrkaz.data.mapper.implementation.ExtractedCommentModelMapper
import com.mrkaz.data.model.ExtractedCommentModel
import com.mrkaz.data.model.LinkContent
import com.mrkaz.sdk.model.ContentExtractorResult
import com.mrkaz.sdk.model.LinkInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtractedCommentModelMapperUnitTest {

    @Test
    fun testExtractedCommentModelMapperHappyCase() {
        // Arrange
        val mentions = listOf("@bill", "@billgate")
        val links = listOf(
            LinkInfo("https://www.genk.vn", "Genk"),
            LinkInfo("https://www.google.com", "Google")
        )
        val contentExtractorResult = ContentExtractorResult(mentions, links)
        val expectedExtractedCommentModel = ExtractedCommentModel(
            mentions,
            listOf(
                LinkContent("https://www.genk.vn", "Genk"),
                LinkContent("https://www.google.com", "Google")
            )
        )
        val mapper = ExtractedCommentModelMapper()

        // Assign
        val mappedModel = mapper.map(contentExtractorResult)

        // Assert
        assertEquals(expectedExtractedCommentModel, mappedModel)
    }

}