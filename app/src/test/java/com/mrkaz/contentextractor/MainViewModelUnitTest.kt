package com.mrkaz.contentextractor

import com.mrkaz.contentextractor.ui.screen.main.MainState
import com.mrkaz.contentextractor.ui.screen.main.MainViewModel
import com.mrkaz.domain.usecase.ExtractCommentUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.orbitmvi.orbit.test.test

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModelUnitTest : KoinTest {

    @MockK
    lateinit var useCase: ExtractCommentUseCase
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        startKoin {
            module {
                mockk<ExtractCommentUseCase>(relaxed = true)
            }
        }
        viewModel = MainViewModel(useCase)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testViewModelInputCommentHappyCase() = runTest {
        val initialState = MainState()
        val input = "@bill and @billgate read https://www.genk.vn and https://www.google.com"
        val expectedResult =
            "{\"mentions\":[\"@bill\",\"@billgate\"],\"links\":[{\"url\":\"https://www.genk.vn\",\"title\":\"Genk\"},{\"url\":\"https://www.google.com\",\"title\":\"Google\"}]}"

        coEvery { useCase.extractComment(any()) } returns expectedResult
        viewModel.test(this, initialState) {
            expectInitialState()
            runOnCreate()
            invokeIntent {
                comment(input)
            }
            expectState { initialState.copy(comment = input) }
            expectState { initialState.copy(comment = input, result = expectedResult) }
            cancelAndIgnoreRemainingItems()
        }
    }

    @Test
    fun testViewModelInputCommentEmptyCase() = runTest {
        val initialState = MainState()
        val input = "bill and billgate"
        val expectedResult = "empty"
        coEvery { useCase.extractComment(any()) } returns expectedResult
        viewModel.test(this, initialState) {
            expectInitialState()
            runOnCreate()
            invokeIntent {
                comment(input)
            }
            expectState { initialState.copy(comment = input) }
            expectState { initialState.copy(comment = input, result = expectedResult) }
            cancelAndIgnoreRemainingItems()
        }
    }

}