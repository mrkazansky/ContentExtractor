package com.mrkaz.sdk.di;

import com.mrkaz.sdk.data.cache.implementation.LinkLruCache;
import com.mrkaz.sdk.data.network.NetworkService;
import com.mrkaz.sdk.data.network.implementation.NetworkServiceImpl;

public class Container {
    public static final NetworkService linkRepository = new NetworkServiceImpl();
    public static final LinkLruCache linkLruCache = new LinkLruCache();
}
