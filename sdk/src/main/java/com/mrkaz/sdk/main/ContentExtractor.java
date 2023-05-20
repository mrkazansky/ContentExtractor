package com.mrkaz.sdk.main;

import com.mrkaz.sdk.model.ContentExtractorResult;

import java.io.Closeable;

public interface ContentExtractor extends Closeable {
    ContentExtractorResult extract(String input);

    void extract(String input, ContentExtractorCallback callback);
}
