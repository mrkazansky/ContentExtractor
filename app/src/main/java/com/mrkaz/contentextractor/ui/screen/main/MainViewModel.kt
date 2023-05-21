package com.mrkaz.contentextractor.ui.screen.main

import androidx.lifecycle.ViewModel
import com.mrkaz.domain.usecase.ExtractCommentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(
    private val commentUseCase: ExtractCommentUseCase
) : ContainerHost<MainState, Nothing>, ViewModel() {
    private val _commentFlow = MutableStateFlow("")
    private val commentFlow = _commentFlow.asStateFlow()

    override val container = container<MainState, Nothing>(
        MainState()
    ) {
        intent {
            commentFlow
                .debounce { if (it.isBlank()) 0 else DEBOUNCE_TIME }
                .flowOn(Dispatchers.IO)
                .flatMapLatest {
                    flowOf(commentUseCase.extractComment(it))
                }
                .collect {
                    reduce {
                        state.copy(result = it)
                    }
                }
        }
    }

    fun comment(comment: String) = intent {
        _commentFlow.emit(comment)
        reduce {
            state.copy(comment = comment)
        }
    }

    override fun onCleared() {
        super.onCleared()
        commentUseCase.close()
    }

    private companion object {
        private const val DEBOUNCE_TIME = 300L
    }
}