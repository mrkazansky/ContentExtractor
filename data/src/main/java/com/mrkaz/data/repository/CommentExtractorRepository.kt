package com.mrkaz.data.repository

import com.mrkaz.data.model.ExtractedCommentModel
import java.io.Closeable

interface CommentExtractorRepository : Closeable {
    fun extractContent(input: String): ExtractedCommentModel
}

