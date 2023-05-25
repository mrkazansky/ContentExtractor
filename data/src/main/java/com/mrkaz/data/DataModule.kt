package com.mrkaz.data

import com.mrkaz.data.mapper.implementation.ExtractedCommentModelMapper
import com.mrkaz.data.mapper.ModelMapper
import com.mrkaz.data.repository.CommentExtractorRepository
import com.mrkaz.data.repository.implementation.CommentExtractorRepositoryImpl
import com.mrkaz.sdk.main.implementation.Builder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::CommentExtractorRepositoryImpl) bind CommentExtractorRepository::class
    singleOf(::ExtractedCommentModelMapper) bind ModelMapper::class
    single { Builder().build() }
}