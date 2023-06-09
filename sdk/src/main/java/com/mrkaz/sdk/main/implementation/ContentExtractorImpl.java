package com.mrkaz.sdk.main.implementation;

import com.mrkaz.sdk.domain.ContentExtractorInteractor;
import com.mrkaz.sdk.main.ContentExtractor;
import com.mrkaz.sdk.main.ContentExtractorCallback;
import com.mrkaz.sdk.model.ContentExtractorResult;

import java.util.concurrent.ExecutorService;

final class ContentExtractorImpl implements ContentExtractor {
    private final ExecutorService executor;
    private final ContentExtractorInteractor contentExtractorInteractor;

    ContentExtractorImpl(ContentExtractorInteractor contentExtractorInteractor, ExecutorService executor) {
        this.contentExtractorInteractor = contentExtractorInteractor;
        this.executor = executor;
    }

    @Override
    public ContentExtractorResult extract(String input) {
        return contentExtractorInteractor.extract(input);
    }

    @Override
    public void extract(String input, ContentExtractorCallback callback) {
        if (executor == null || callback == null) return;
        executor.execute(() -> {
            ContentExtractorResult result = contentExtractorInteractor.extract(input);
            callback.onContentResult(result);
        });
    }

    @Override
    public void close() {
        if (executor == null) return;
        executor.shutdown();
    }
}
