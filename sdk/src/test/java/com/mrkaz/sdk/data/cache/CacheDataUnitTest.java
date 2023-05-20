package com.mrkaz.sdk.data.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CacheDataUnitTest {
    @Test
    public void testCacheDataHappyCase() {
        // Arrange
        String value = "Kenh14, Vietname lake";
        long timestamp = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1);
        long timeout = 1;

        // Assign
        CacheData<String> cacheData = new CacheData<>(value, timestamp, timeout, TimeUnit.MILLISECONDS);
        String extractedValue = cacheData.getValue();
        long extractedTimestamp = cacheData.getTimestamp();
        long extractedTimeout = cacheData.getTimeout();

        // Assert
        assertNotNull(extractedValue);
        assertEquals(value, extractedValue);

        assertEquals(timestamp, extractedTimestamp);
        assertEquals(timeout, extractedTimeout);
    }

    @Test
    public void testCacheDataExpired() {
        // Arrange
        String value = "Kenh14, Vietname lake";
        long timestamp = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3);
        long timeout = 1;

        // Assign
        CacheData<String> cacheData = new CacheData<>(value, timestamp, timeout, TimeUnit.MILLISECONDS);
        boolean isExpired = cacheData.isExpired();

        // Assert
        assertTrue(isExpired);
    }

    @Test
    public void testCacheDataNotExpired() {
        // Arrange
        String value = "Kenh14, Vietname lake";
        long timestamp = System.currentTimeMillis();
        long timeout = 10;

        // Assign
        CacheData<String> cacheData = new CacheData<>(value, timestamp, timeout, TimeUnit.MILLISECONDS);
        boolean isExpired = cacheData.isExpired();

        // Assert
        assertFalse(isExpired);
    }
}
