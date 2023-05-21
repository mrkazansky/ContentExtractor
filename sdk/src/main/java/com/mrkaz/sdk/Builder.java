package com.mrkaz.sdk;

import android.os.Handler;
import android.os.Looper;

import com.mrkaz.sdk.data.extractor.Extractor;
import com.mrkaz.sdk.data.extractor.implermentation.LinkExtractor;
import com.mrkaz.sdk.data.extractor.implermentation.MentionExtractor;
import com.mrkaz.sdk.di.Container;
import com.mrkaz.sdk.domain.ContentExtractorInteractor;
import com.mrkaz.sdk.domain.implementation.ContentExtractorInteractorImpl;
import com.mrkaz.sdk.main.ContentExtractor;
import com.mrkaz.sdk.main.implementation.ContentExtractorImpl;
import com.mrkaz.sdk.model.LinkInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Builder {
    private Extractor<LinkInfo> linkExtractor = new LinkExtractor(Container.linkRepository, Container.linkLruCache);
    private Extractor<String> mentionExtractor = new MentionExtractor();
    private ExecutorService executor;

    public Builder setLinkExtractor(Extractor<LinkInfo> extractor) {
        this.linkExtractor = extractor;
        return this;
    }

    public Builder setMentionExtractor(Extractor<String> extractor) {
        this.mentionExtractor = extractor;
        return this;
    }

    public Builder setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public ContentExtractor build() {
        ContentExtractorInteractor contentExtractor = new ContentExtractorInteractorImpl(linkExtractor, mentionExtractor);
        return new ContentExtractorImpl(contentExtractor, executor);
    }
}

