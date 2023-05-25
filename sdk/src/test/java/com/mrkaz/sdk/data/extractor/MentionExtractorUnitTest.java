package com.mrkaz.sdk.data.extractor;

import static org.junit.Assert.assertEquals;

import com.mrkaz.sdk.data.extractor.implermentation.MentionExtractor;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MentionExtractorUnitTest {
    @Test
    public void testMentionExtractorHappyCase() {
        // Arrange
        Extractor<String> mentionExtractor = new MentionExtractor();
        String input = "Hey, @bill and @billClinton! What's up?";
        List<String> expectedMentions = Arrays.asList("@bill", "@billClinton");

        // Assign
        List<String> extractedMentions = mentionExtractor.extract(input);

        // Assert
        assertEquals(expectedMentions, extractedMentions);
    }

    @Test
    public void testMentionExtractorNoMentionsCase() {
        // Arrange
        Extractor<String> mentionExtractor = new MentionExtractor();
        String input = "How are you?";
        List<String> expectedMentions = Collections.emptyList();

        // Assign
        List<String> extractedMentions = mentionExtractor.extract(input);

        // Assert
        assertEquals(expectedMentions, extractedMentions);
    }

    @Test
    public void testMentionExtractorNullInputCase() {
        // Arrange
        Extractor<String> mentionExtractor = new MentionExtractor();
        String input = null;
        List<String> expectedMentions = Collections.emptyList();

        // Assign
        List<String> extractedMentions = mentionExtractor.extract(input);

        // Assert
        assertEquals(expectedMentions, extractedMentions);
    }
}
