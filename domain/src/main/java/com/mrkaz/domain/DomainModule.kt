package com.mrkaz.domain

import com.google.gson.GsonBuilder
import com.mrkaz.domain.usecase.implementation.ExtractContentUseCaseImpl
import com.mrkaz.domain.usecase.ExtractCommentUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::ExtractContentUseCaseImpl) bind ExtractCommentUseCase::class
    single { GsonBuilder().setPrettyPrinting().create() }
}
