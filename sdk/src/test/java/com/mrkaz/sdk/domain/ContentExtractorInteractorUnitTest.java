package com.mrkaz.sdk.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mrkaz.sdk.data.extractor.Extractor;
import com.mrkaz.sdk.domain.implementation.ContentExtractorInteractorImpl;
import com.mrkaz.sdk.model.ContentExtractorResult;
import com.mrkaz.sdk.model.LinkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class ContentExtractorInteractorUnitTest {
    @Mock
    private Extractor<LinkInfo> mockLinkExtractor;

    @Mock
    private Extractor<String> mockMentionExtractor;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExtractHappyCase() {
        // Arrange
        String input = "@bill read this link https://www.google.com";
        List<String> mockMentions = Collections.singletonList("@bill");
        List<LinkInfo> mockLinks = Collections.singletonList(new LinkInfo("https://www.google.com", "Google"));

        when(mockMentionExtractor.extract(input)).thenReturn(mockMentions);
        when(mockLinkExtractor.extract(input)).thenReturn(mockLinks);

        ContentExtractorInteractor interactor = new ContentExtractorInteractorImpl(mockLinkExtractor, mockMentionExtractor);

        // Assign
        ContentExtractorResult result = interactor.extract(input);

        // Assert
        assertEquals(result.getMentions().get(0), mockMentions.get(0));
        assertEquals(result.getLinks().get(0).getTitle(), mockLinks.get(0).getTitle());
        assertEquals(result.getLinks().get(0).getUrl(), mockLinks.get(0).getUrl());

        // Verify calls
        verify(mockMentionExtractor, times(1)).extract(input);
        verify(mockLinkExtractor, times(1)).extract(input);
    }

    @Test
    public void testExtractNoMatchingCase() {
        // Arrange
        String input = "read this link google.com";
        List<String> mockMentions = Collections.emptyList();
        List<LinkInfo> mockLinks = Collections.emptyList();

        when(mockMentionExtractor.extract(input)).thenReturn(mockMentions);
        when(mockLinkExtractor.extract(input)).thenReturn(mockLinks);

        ContentExtractorInteractor interactor = new ContentExtractorInteractorImpl(mockLinkExtractor, mockMentionExtractor);

        // Assign
        ContentExtractorResult result = interactor.extract(input);

        // Assert
        assertEquals(result.getMentions().size(), mockMentions.size());
        assertEquals(result.getLinks().size(), mockLinks.size());

        // Verify calls
        verify(mockMentionExtractor, times(1)).extract(input);
        verify(mockLinkExtractor, times(1)).extract(input);
    }

    @Test
    public void testExtractFailedCase() {
        // Arrange
        String input = "read this link";

        when(mockMentionExtractor.extract(input)).thenThrow(new RuntimeException("Extractor error"));
        ContentExtractorInteractorImpl interactor = new ContentExtractorInteractorImpl(mockLinkExtractor, mockMentionExtractor);

        try {
            // Assign
            interactor.extract(input);
        } catch (Exception e) {
            // Assert
            assertEquals("Extractor error", e.getMessage());

            // Verify extractor calls
            verify(mockMentionExtractor, times(1)).extract(input);
            verify(mockLinkExtractor, never()).extract(input);
        }
    }
}
