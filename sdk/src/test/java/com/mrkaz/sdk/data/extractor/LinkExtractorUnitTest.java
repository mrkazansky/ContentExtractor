package com.mrkaz.sdk.data.extractor;

import static com.mrkaz.sdk.data.extractor.implermentation.LinkExtractor.EMPTY_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mrkaz.sdk.data.cache.Cache;
import com.mrkaz.sdk.data.cache.CacheData;
import com.mrkaz.sdk.data.extractor.implermentation.LinkExtractor;
import com.mrkaz.sdk.data.network.NetworkService;
import com.mrkaz.sdk.model.LinkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LinkExtractorUnitTest {

    @Mock
    NetworkService networkService;

    @Mock
    Cache<String, CacheData<String>> cache;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLinkExtractorWithoutCacheHappyCase() throws IOException {
        // Arrange
        String expectedUrl = "https://www.google.com";
        String expectedTitle = "Google";
        String expectedHtml = "<html><head><title>Google</title></head><body></body></html>";

        when(networkService.fetchWebContent(anyString())).thenReturn(expectedHtml);
        when(cache.isCached(anyString())).thenReturn(false);

        Extractor<LinkInfo> linkExtractor = new LinkExtractor(networkService, cache);
        String input = "Verify this link: https://www.google.com!";

        // Assign
        List<LinkInfo> extractedLinks = linkExtractor.extract(input);

        // Assert
        assertEquals(1, extractedLinks.size());
        LinkInfo extractedLinkInfo = extractedLinks.get(0);
        assertEquals(expectedUrl, extractedLinkInfo.getUrl());
        assertEquals(expectedTitle, extractedLinkInfo.getTitle());

        // Verify cache interaction
        verify(cache, times(1)).isCached(expectedUrl);
        verify(cache, times(1)).put(eq(expectedUrl), any(CacheData.class));

        // Verify network service interaction
        verify(networkService, times(1)).fetchWebContent(expectedUrl);
    }

    @Test
    public void testLinkExtractorWithCacheHappyCase() throws IOException {
        // Arrange
        String expectedUrl = "https://www.google.com";
        String expectedTitle = "Google";
        String expectedHtml = "<html><head><title>Google</title></head><body></body></html>";
        CacheData<String> expectedCacheData = new CacheData<>(expectedTitle, System.currentTimeMillis(), 1000, TimeUnit.MILLISECONDS);

        when(networkService.fetchWebContent(anyString())).thenReturn(expectedHtml);
        when(cache.isCached(anyString())).thenReturn(true);
        when(cache.get(anyString())).thenReturn(expectedCacheData);

        Extractor<LinkInfo> linkExtractor = new LinkExtractor(networkService, cache);
        String input = "Verify this link: https://www.google.com!";

        // Assign
        List<LinkInfo> extractedLinks = linkExtractor.extract(input);

        // Assert
        assertEquals(1, extractedLinks.size());
        LinkInfo extractedLinkInfo = extractedLinks.get(0);
        assertEquals(expectedUrl, extractedLinkInfo.getUrl());
        assertEquals(expectedTitle, extractedLinkInfo.getTitle());

        // Verify cache interaction
        verify(cache, times(1)).isCached(expectedUrl);
        verify(cache, times(1)).get(anyString());

        // Verify network service interaction
        verify(networkService, never()).fetchWebContent(expectedUrl);
    }

    @Test
    public void testLinkExtractorEmptyTitleCase() throws IOException {
        // Arrange
        String expectedUrl = "https://www.google.com";
        String expectedTitle = EMPTY_TITLE;
        String expectedHtml = "<html><head></head><body></body></html>";

        when(networkService.fetchWebContent(anyString())).thenReturn(expectedHtml);
        when(cache.isCached(anyString())).thenReturn(false);

        Extractor<LinkInfo> linkExtractor = new LinkExtractor(networkService, cache);
        String input = "Verify this link: https://www.google.com!";

        // Assign
        List<LinkInfo> extractedLinks = linkExtractor.extract(input);

        // Assert
        assertEquals(1, extractedLinks.size());
        LinkInfo extractedLinkInfo = extractedLinks.get(0);
        assertEquals(expectedUrl, extractedLinkInfo.getUrl());
        assertEquals(expectedTitle, extractedLinkInfo.getTitle());

        // Verify cache interaction
        verify(cache, times(1)).isCached(expectedUrl);
        verify(cache, times(1)).put(eq(expectedUrl), any(CacheData.class));

        // Verify network service interaction
        verify(networkService, times(1)).fetchWebContent(expectedUrl);
    }

    @Test
    public void testLinkExtractorNetworkFailedCase() throws IOException {
        // Arrange
        when(networkService.fetchWebContent(anyString())).thenThrow(new IOException());
        when(cache.isCached(anyString())).thenReturn(false);

        Extractor<LinkInfo> linkExtractor = new LinkExtractor(networkService, cache);
        String input = "Verify this link: https://www.google.com!";

        // Assign
        List<LinkInfo> extractedLinks = linkExtractor.extract(input);

        // Assert
        assertEquals(0, extractedLinks.size());

        // Verify cache interaction
        verify(cache, times(1)).isCached(anyString());
        verify(cache, never()).put(anyString(), any(CacheData.class));

        // Verify network service interaction
        verify(networkService, times(1)).fetchWebContent(anyString());
    }

    @Test
    public void testLinkExtractorNoUrlCase() throws IOException {
        // Arrange
        when(cache.isCached(anyString())).thenReturn(false);

        Extractor<LinkInfo> linkExtractor = new LinkExtractor(networkService, cache);
        String input = "Verify this link: www.google.com";

        // Assign
        List<LinkInfo> extractedLinks = linkExtractor.extract(input);

        // Assert
        assertEquals(0, extractedLinks.size());

        // Verify cache interaction
        verify(cache, never()).isCached(anyString());
        verify(cache, never()).put(anyString(), any(CacheData.class));

        // Verify network service interaction
        verify(networkService, never()).fetchWebContent(anyString());
    }
}
