package com.mrkaz.sdk.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Handler;

import com.mrkaz.sdk.domain.ContentExtractorInteractor;
import com.mrkaz.sdk.main.implementation.ContentExtractorImpl;
import com.mrkaz.sdk.model.ContentExtractorResult;
import com.mrkaz.sdk.model.LinkInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ContentExtractorUnitTest {


    @Mock
    private ContentExtractorInteractor mockContentExtractorInteractor;

    @Mock
    private Handler mockResultHandler;

    @Mock
    ContentExtractorCallback mockCallback;

    @Mock
    private ExecutorService mockExecutorService;

    private ContentExtractor contentExtractor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        contentExtractor = new ContentExtractorImpl(mockContentExtractorInteractor, mockExecutorService, mockResultHandler);
    }

    @After
    public void tearDown() {
        mockExecutorService.shutdown();
    }

    @Test
    public void testExtractAsynchronousHappyCase() {
        // Arrange
        String input = "@bill read this link https://www.google.com";
        List<String> expectedMentions = Collections.singletonList("@bill");
        List<LinkInfo> expectedLinks = Collections.singletonList(new LinkInfo("https://www.google.com", "Google"));
        ContentExtractorResult mockResult = new ContentExtractorResult(expectedMentions, expectedLinks);

        doAnswer(invocation -> {
                    Runnable runnable = invocation.getArgument(0);
                    runnable.run();
                    return null;
                }
        ).when(mockExecutorService).execute(any(Runnable.class));
        ArgumentCaptor<Runnable> callbackCaptor = ArgumentCaptor.forClass(Runnable.class);
        when(mockContentExtractorInteractor.extract(input)).thenReturn(mockResult);

        // Assign
        contentExtractor.extract(input, mockCallback);

        // Assign
        verify(mockExecutorService, times(1)).execute(any(Runnable.class));
        verify(mockContentExtractorInteractor, times(1)).extract(input);
        verify(mockResultHandler, times(1)).post(callbackCaptor.capture());
        callbackCaptor.getValue().run();
        verify(mockCallback, times(1)).onContentResult(mockResult);
    }

    @Test
    public void testExtractSynchronousHappyCase() {
        // Arrange
        String input = "@bill read this link https://www.google.com";
        List<String> expectedMentions = Collections.singletonList("@bill");
        List<LinkInfo> expectedLinks = Collections.singletonList(new LinkInfo("https://www.google.com", "Google"));
        ContentExtractorResult mockResult = new ContentExtractorResult(expectedMentions, expectedLinks);

        when(mockContentExtractorInteractor.extract(input)).thenReturn(mockResult);

        // Assign
        ContentExtractorResult result = contentExtractor.extract(input);

        // Assert
        assertEquals(mockResult, result);

        // Verify calls
        verify(mockContentExtractorInteractor, times(1)).extract(input);
    }

    @Test
    public void testClose() throws IOException {
        // Assign
        contentExtractor.close();

        // Assert
        verify(mockResultHandler, times(1)).removeCallbacksAndMessages(null);
        verify(mockExecutorService, times(1)).shutdown();
    }
}
