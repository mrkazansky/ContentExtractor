@file:OptIn(ExperimentalCoroutinesApi::class)

package com.mrkaz.contentextractor.ui.screen.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mrkaz.contentextractor.R
import com.mrkaz.contentextractor.ui.theme.Padding.BORDER_SPACING
import com.mrkaz.contentextractor.ui.theme.Padding.ELEMENT_SPACING
import com.mrkaz.contentextractor.ui.theme.Shapes
import com.mrkaz.contentextractor.ui.theme.Size.IMAGE_HOLDER_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@ExperimentalAnimationApi
@FlowPreview
@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.collectAsState()
    MainPage(state = state, onValueChange = { viewModel.comment(it) })
}

@ExperimentalAnimationApi
@Composable
fun MainPage(state: MainState, onValueChange: (String) -> Unit) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            shape = Shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = BORDER_SPACING, end = BORDER_SPACING, top = BORDER_SPACING)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = state.result,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(scroll)
                        .padding(horizontal = BORDER_SPACING, vertical = ELEMENT_SPACING)
                )
                this@Column.AnimatedVisibility(
                    visible = state.result.isEmpty()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_ghost))
                        LottieAnimation(
                            composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(IMAGE_HOLDER_SIZE)
                        )
                        AnimatedContent(
                            targetState = state.comment.isEmpty(),
                            transitionSpec = {
                                EnterTransition.None with ExitTransition.None
                            }
                        ) { targetState ->
                            Text(
                                modifier = Modifier.animateEnterExit(
                                    enter = scaleIn(),
                                    exit = scaleOut()
                                ),
                                text = if (targetState)
                                    stringResource(id = R.string.result_holder)
                                else
                                    stringResource(id = R.string.result_nothing)
                            )
                        }

                    }
                }
            }
        }
        OutlinedTextField(
            value = state.comment,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = { Text(stringResource(id = R.string.input_holder)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(BORDER_SPACING),
            singleLine = true
        )
    }
}