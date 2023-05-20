package com.mrkaz.sdk.domain;

import com.mrkaz.sdk.model.ContentExtractorResult;

public interface ContentExtractorInteractor {
    ContentExtractorResult extract(String input);
}
