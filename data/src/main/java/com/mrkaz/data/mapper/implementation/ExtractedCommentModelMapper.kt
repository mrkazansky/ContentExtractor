package com.mrkaz.data.mapper.implementation

import com.mrkaz.data.mapper.ModelMapper
import com.mrkaz.data.model.ExtractedCommentModel
import com.mrkaz.data.model.LinkContent
import com.mrkaz.sdk.model.ContentExtractorResult

class ExtractedCommentModelMapper : ModelMapper<ContentExtractorResult, ExtractedCommentModel>() {
    override fun map(data: ContentExtractorResult): ExtractedCommentModel {
        return ExtractedCommentModel(
            data.mentions,
            data.links.map { LinkContent(it.url, it.title) })
    }
}