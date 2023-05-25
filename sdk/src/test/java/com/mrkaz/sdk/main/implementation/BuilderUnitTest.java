package com.mrkaz.sdk.main.implementation;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mrkaz.sdk.data.extractor.Extractor;
import com.mrkaz.sdk.main.ContentExtractor;
import com.mrkaz.sdk.main.implementation.Builder;
import com.mrkaz.sdk.model.LinkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.concurrent.ExecutorService;

public class BuilderUnitTest {
    @Mock
    private Extractor<LinkInfo> mockLinkExtractor;

    @Mock
    private Extractor<String> mockMentionExtractor;

    @Mock
    private ExecutorService mockExecutorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuildHappyCase() {
        // Arrange
        String inputString = "@bill is reading https://www.google.com";
        ContentExtractor extractor = new Builder()
                .setLinkExtractor(mockLinkExtractor)
                .setMentionExtractor(mockMentionExtractor)
                .setExecutor(mockExecutorService)
                .build();
        when(mockLinkExtractor.extract(anyString())).thenReturn(Collections.emptyList());
        when(mockMentionExtractor.extract(anyString())).thenReturn(Collections.emptyList());

        // Assign
        extractor.extract(inputString);

        // Assert
        assertNotNull(extractor);
        verify(mockLinkExtractor, times(1)).extract(inputString);
        verify(mockMentionExtractor, times(1)).extract(inputString);
    }
}
