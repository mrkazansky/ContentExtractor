package com.mrkaz.sdk.main.implementation;

import android.os.Handler;

import com.mrkaz.sdk.domain.ContentExtractorInteractor;
import com.mrkaz.sdk.main.ContentExtractor;
import com.mrkaz.sdk.main.ContentExtractorCallback;
import com.mrkaz.sdk.model.ContentExtractorResult;

import java.util.concurrent.ExecutorService;

public class ContentExtractorImpl implements ContentExtractor {
    private final ExecutorService executor;
    private final Handler resultHandler;
    private final ContentExtractorInteractor contentExtractorInteractor;

    public ContentExtractorImpl(ContentExtractorInteractor contentExtractorInteractor, ExecutorService executor, Handler resultHandler) {
        this.contentExtractorInteractor = contentExtractorInteractor;
        this.executor = executor;
        this.resultHandler = resultHandler;
    }

    @Override
    public ContentExtractorResult extract(String input) {
        return contentExtractorInteractor.extract(input);
    }

    @Override
    public void extract(String input, ContentExtractorCallback callback) {
        executor.execute(() -> {
            ContentExtractorResult result = contentExtractorInteractor.extract(input);
            resultHandler.post(() -> callback.onContentResult(result));
        });
    }

    @Override
    public void close() {
        resultHandler.removeCallbacksAndMessages(null);
        executor.shutdown();
    }
}
