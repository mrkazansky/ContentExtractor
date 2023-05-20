package com.mrkaz.domain.usecase.implementation

import com.google.gson.Gson
import com.mrkaz.data.repository.CommentExtractorRepository
import com.mrkaz.domain.usecase.ExtractCommentUseCase

class ExtractContentUseCaseImpl(
    private val repository: CommentExtractorRepository,
    private val gson: Gson
) : ExtractCommentUseCase {

    private companion object {
        private const val EMPTY = ""
    }

    override suspend fun extractComment(input: String): String {
        val extractedContent = repository.extractContent(input)
        return if (extractedContent.mentions.isEmpty() && extractedContent.links.isEmpty()) {
            EMPTY
        } else {
            gson.toJson(extractedContent)
        }
    }

    override fun close() {
        repository.close()
    }
}