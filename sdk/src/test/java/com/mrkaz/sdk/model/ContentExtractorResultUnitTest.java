package com.mrkaz.sdk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ContentExtractorResultUnitTest {
    @Test
    public void testContentExtractorResult() {
        // Arrange
        List<String> mentions = Arrays.asList("@bill", "@billgate");
        List<LinkInfo> links = Arrays.asList(
                new LinkInfo("https://www.google.com", "Google"),
                new LinkInfo("https://www.genk.com", "Genk")
        );

        // Assign
        ContentExtractorResult result = new ContentExtractorResult(mentions, links);
        List<String> extractedMentions = result.getMentions();
        List<LinkInfo> extractedLinks = result.getLinks();

        // Assert
        assertNotNull(extractedMentions);
        assertEquals(2, extractedMentions.size());
        assertEquals("@bill", extractedMentions.get(0));
        assertEquals("@billgate", extractedMentions.get(1));

        assertNotNull(extractedLinks);
        assertEquals(2, extractedLinks.size());
        assertEquals("https://www.google.com", extractedLinks.get(0).getUrl());
        assertEquals("Google", extractedLinks.get(0).getTitle());
        assertEquals("https://www.genk.com", extractedLinks.get(1).getUrl());
        assertEquals("Genk", extractedLinks.get(1).getTitle());
    }
}
