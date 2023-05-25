package com.mrkaz.sdk.domain.implementation;

import com.mrkaz.sdk.data.extractor.Extractor;
import com.mrkaz.sdk.domain.ContentExtractorInteractor;
import com.mrkaz.sdk.model.ContentExtractorResult;
import com.mrkaz.sdk.model.LinkInfo;

import java.util.List;

public final class ContentExtractorInteractorImpl implements ContentExtractorInteractor {
    private final Extractor<LinkInfo> linkExtractor;
    private final Extractor<String> mentionExtractor;

    public ContentExtractorInteractorImpl(Extractor<LinkInfo> linkExtractor, Extractor<String> mentionExtractor) {
        this.linkExtractor = linkExtractor;
        this.mentionExtractor = mentionExtractor;
    }

    @Override
    public ContentExtractorResult extract(String input) {
        List<String> mentions = mentionExtractor.extract(input);
        List<LinkInfo> links = linkExtractor.extract(input);
        return new ContentExtractorResult(mentions, links);
    }
}
