package com.mrkaz.sdk.main;

import com.mrkaz.sdk.model.ContentExtractorResult;

public interface ContentExtractorCallback {
    void onContentResult(ContentExtractorResult result);
}
