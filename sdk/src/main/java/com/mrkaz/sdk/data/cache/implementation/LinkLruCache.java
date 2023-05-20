package com.mrkaz.sdk.data.cache.implementation;

import android.util.LruCache;

import com.mrkaz.sdk.data.cache.Cache;
import com.mrkaz.sdk.data.cache.CacheData;

public class LinkLruCache implements Cache<String, CacheData<String>> {
    private static final int CACHE_SIZE = 100;
    private final LruCache<String, CacheData<String>> cache = new LruCache<>(CACHE_SIZE);

    @Override
    public CacheData<String> get(String key) {
        return cache.get(key);
    }

    @Override
    public void put(String key, CacheData<String> value) {
        cache.put(key, value);
    }

    @Override
    public boolean isCached(String key) {
        CacheData<String> cachedData = cache.get(key);
        return cachedData != null && !cachedData.isExpired();
    }
}
