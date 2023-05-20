package com.mrkaz.domain.usecase

import java.io.Closeable

interface ExtractCommentUseCase : Closeable {
    suspend fun extractComment(input: String): String
}
