package com.mrkaz.data.repository.implementation

import com.mrkaz.data.mapper.ModelMapper
import com.mrkaz.data.model.ExtractedCommentModel
import com.mrkaz.data.repository.CommentExtractorRepository
import com.mrkaz.sdk.main.ContentExtractor
import com.mrkaz.sdk.model.ContentExtractorResult

class CommentExtractorRepositoryImpl(
    private val extractor: ContentExtractor,
    private val mapper: ModelMapper<ContentExtractorResult, ExtractedCommentModel>
) : CommentExtractorRepository {

    override fun extractContent(input: String): ExtractedCommentModel {
        return mapper.map(extractor.extract(input))
    }

    override fun close() {
        extractor.close()
    }
}