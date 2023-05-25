package com.mrkaz.contentextractor

import android.os.SystemClock
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import com.mrkaz.contentextractor.ui.constant.UiTag.TAG_CONTENT
import com.mrkaz.contentextractor.ui.constant.UiTag.TAG_INPUT_TEXT
import com.mrkaz.contentextractor.ui.constant.UiTag.TAG_PLACEHOLDER_VIEW
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
@ExperimentalAnimationApi
class MainScreenInstrumentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testMainScreenInit() {
        val expectedText = "Input your comment here"
        // Assert
        composeTestRule.onNodeWithTag(TAG_INPUT_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PLACEHOLDER_VIEW).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_INPUT_TEXT).assertTextContains(expectedText)
    }

    @Test
    fun testMainScreenInputInvalidComment() {
        // Arrange
        val expectedText = "[]"
        val inputText = "bill is reading www.google.com"

        // Assign
        composeTestRule.onNodeWithTag(TAG_INPUT_TEXT)
            .performTextInput(inputText)
        composeTestRule.onNodeWithTag(TAG_INPUT_TEXT)
            .assertTextContains(inputText)
        SystemClock.sleep(500)

        // Assert
        composeTestRule.onNodeWithTag(TAG_PLACEHOLDER_VIEW).assertIsDisplayed()
        val text = composeTestRule.onNodeWithTag(TAG_CONTENT).fetchSemanticsNode().config[Text]
        assertEquals(text.toString(), expectedText)
    }

    @Test
    fun testMainScreenInputValidComment() {
        // Arrange
        val expectedLinkTitle = "Google"
        val expectedMention = "@bill"
        val inputText = "@bill is reading https://www.google.com"

        // Assign
        composeTestRule.onNodeWithTag(TAG_INPUT_TEXT)
            .performTextInput(inputText)
        composeTestRule.onNodeWithTag(TAG_INPUT_TEXT)
            .assertTextContains(inputText)
        SystemClock.sleep(1000)

        // Assert
        composeTestRule.onNodeWithTag(TAG_PLACEHOLDER_VIEW).assertDoesNotExist()
        val text = composeTestRule.onNodeWithTag(TAG_CONTENT).fetchSemanticsNode().config[Text]
        assertTrue(text.toString().contains(expectedLinkTitle))
        assertTrue(text.toString().contains(expectedMention))

    }
}