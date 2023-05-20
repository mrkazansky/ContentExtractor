package com.mrkaz.sdk.model;

public class LinkInfo {
    private final String url;
    private final String title;

    public LinkInfo(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
