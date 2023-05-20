package com.mrkaz.sdk.data.cache;

public interface Cache<K, V> {
    V get(K key);

    void put(K key, V value);

    boolean isCached(K key);
}
