package com.mrkaz.sdk.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LinkInfoUnitTest {
    @Test
    public void testLinkInfo() {
        // Arrange
        String url = "https://www.google.com";
        String title = "Google";

        // Assign
        LinkInfo linkInfo = new LinkInfo(url, title);
        String extractedUrl = linkInfo.getUrl();
        String extractedTitle = linkInfo.getTitle();

        // Assert
        assertNotNull(extractedUrl);
        assertEquals(url, extractedUrl);

        assertNotNull(extractedTitle);
        assertEquals(title, extractedTitle);
    }
}
