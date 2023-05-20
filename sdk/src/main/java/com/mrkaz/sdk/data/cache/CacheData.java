package com.mrkaz.sdk.data.cache;

import java.util.concurrent.TimeUnit;

public class CacheData<T> {
    private final T value;
    private final long timestamp;
    private final long timeout;

    public CacheData(T value, long timestamp, long timeout, TimeUnit timeUnit) {
        this.value = value;
        this.timestamp = timestamp;
        this.timeout = timeUnit.toMillis(timeout);
    }

    public T getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getTimeout(){
        return timeout;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() - timestamp) > timeout;
    }
}