@file:OptIn(FlowPreview::class)

package com.mrkaz.contentextractor

import com.mrkaz.contentextractor.ui.screen.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val appModule = module {
    viewModelOf(::MainViewModel)
}
