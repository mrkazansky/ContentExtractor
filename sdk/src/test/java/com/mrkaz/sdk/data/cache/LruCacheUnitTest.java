package com.mrkaz.sdk.data.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.os.Build;

import com.mrkaz.sdk.data.cache.implementation.LinkLruCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class LruCacheUnitTest {
    @Test
    public void testLinkLruCacheHappyCase() {
        // Arrange
        String key = "expected-key";
        String value = "expected-value";
        CacheData<String> cacheData = new CacheData<>(value, System.currentTimeMillis(), 1000, TimeUnit.MILLISECONDS);

        // Assign
        Cache<String, CacheData<String>> linkLruCache = new LinkLruCache();
        linkLruCache.put(key, cacheData);
        CacheData<String> retrievedData = linkLruCache.get(key);

        // Assert
        assertNotNull(retrievedData);
        assertEquals(value, retrievedData.getValue());
    }

    @Test
    public void testLinkLruCacheExpired() {
        // Arrange
        String key = "expected-key";
        String value = "expected-value";
        CacheData<String> expiredCacheData = new CacheData<>(value, System.currentTimeMillis() - 2000, 1000, TimeUnit.MILLISECONDS);

        // Assign
        Cache<String, CacheData<String>> linkLruCache = new LinkLruCache();
        linkLruCache.put(key, expiredCacheData);
        boolean isCached = linkLruCache.isCached(key);

        // Assert
        assertFalse(isCached);
    }

    @Test
    public void testLinkLruCacheNotExpired() {
        // Arrange
        String key = "expected-key";
        String value = "expected-value";
        CacheData<String> validCacheData = new CacheData<>(value, System.currentTimeMillis(), 1000, TimeUnit.MILLISECONDS);

        // Assign
        Cache<String, CacheData<String>> linkLruCache = new LinkLruCache();
        linkLruCache.put(key, validCacheData);
        boolean isCached = linkLruCache.isCached(key);

        // Assert
        assertTrue(isCached);
    }


    @Test
    public void testLinkLruCacheSizeLimit() {
        // Arrange
        int cacheSize = 2;
        Cache<String, CacheData<String>> linkLruCache = new LinkLruCache(cacheSize);
        linkLruCache.put("key1", new CacheData<>("value1", System.currentTimeMillis(), 1000, TimeUnit.MILLISECONDS));
        linkLruCache.put("key2", new CacheData<>("value2", System.currentTimeMillis(), 1000, TimeUnit.MILLISECONDS));
        linkLruCache.put("key3", new CacheData<>("value3", System.currentTimeMillis(), 1000, TimeUnit.MILLISECONDS));

        // Assign
        CacheData<String> retrievedData = linkLruCache.get("key1");

        // Assert
        assertNull(retrievedData);
    }
}
