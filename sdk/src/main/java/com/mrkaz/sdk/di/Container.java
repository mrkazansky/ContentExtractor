package com.mrkaz.sdk.di;

import com.mrkaz.sdk.data.cache.implementation.LinkLruCache;
import com.mrkaz.sdk.data.extractor.Extractor;
import com.mrkaz.sdk.data.network.NetworkService;
import com.mrkaz.sdk.data.network.implementation.NetworkServiceImpl;
import com.mrkaz.sdk.domain.ContentExtractorInteractor;
import com.mrkaz.sdk.domain.implementation.ContentExtractorInteractorImpl;
import com.mrkaz.sdk.model.LinkInfo;

public class Container {
    public static final NetworkService linkRepository = new NetworkServiceImpl();
    public static final LinkLruCache linkLruCache = new LinkLruCache();

    public static ContentExtractorInteractor getContentExtractorInteractor(Extractor<LinkInfo> linkExtractor, Extractor<String> mentionExtractor) {
        return new ContentExtractorInteractorImpl(linkExtractor, mentionExtractor);
    }
}
