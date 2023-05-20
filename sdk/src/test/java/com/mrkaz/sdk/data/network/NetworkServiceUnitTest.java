package com.mrkaz.sdk.data.network;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mrkaz.sdk.data.network.implementation.NetworkServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NetworkServiceImpl.class)
public class NetworkServiceUnitTest {
    @Mock
    private URLConnection mockConnection;

    @Mock
    private BufferedReader mockReader;

    @Mock
    private InputStreamReader mockInputStream;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFetchWebContentHappyCase() throws Exception {
        // Arrange
        NetworkService networkService = new NetworkServiceImpl();

        URL mockUrl = PowerMockito.mock(URL.class);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(mockUrl);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        when(mockReader.readLine()).thenReturn("<div>", "</div>", null);
        PowerMockito.whenNew(InputStreamReader.class).withAnyArguments().thenReturn(mockInputStream);
        PowerMockito.whenNew(BufferedReader.class).withAnyArguments().thenReturn(mockReader);

        // Assign
        String content = networkService.fetchWebContent("https://www.google.com");

        // Assertions
        assertEquals("<div></div>", content);

        // Verify URL and connection opening
        verify(mockUrl, times(1)).openConnection();

        // Verify BufferedReader interaction
        verify(mockReader, times(3)).readLine();
        verify(mockReader, times(1)).close();
    }
}
