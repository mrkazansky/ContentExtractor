package com.mrkaz.sdk.model;

import java.util.List;

public class ContentExtractorResult {
    private final List<String> mentions;
    private final List<LinkInfo> links;

    public ContentExtractorResult(List<String> mentions, List<LinkInfo> links) {
        this.mentions = mentions;
        this.links = links;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public List<LinkInfo> getLinks() {
        return links;
    }
}
