package com.mrkaz.sdk.data.extractor.implermentation;

import android.util.Patterns;

import com.mrkaz.sdk.data.cache.Cache;
import com.mrkaz.sdk.data.cache.CacheData;
import com.mrkaz.sdk.data.extractor.Extractor;
import com.mrkaz.sdk.data.network.NetworkService;
import com.mrkaz.sdk.model.LinkInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor extends Extractor<LinkInfo> {
    private static final Pattern LINK_PATTERN = Patterns.WEB_URL;
    private static final String EMPTY_TITLE = "Empty title";
    private static final TimeUnit CACHE_TIME_UNIT = TimeUnit.MINUTES;
    private static final int CACHE_TIMEOUT = 10;

    private final NetworkService linkRepository;
    private final Cache<String, CacheData<String>> cache;

    public LinkExtractor(NetworkService linkRepository, Cache<String, CacheData<String>> cache) {
        this.linkRepository = linkRepository;
        this.cache = cache;
    }

    @Override
    public List<LinkInfo> extract(String input) {
        List<LinkInfo> links = new ArrayList<>();
        Matcher matcher = LINK_PATTERN.matcher(input);
        while (matcher.find()) {
            String url = matcher.group();
            if (cache.isCached(url)) {
                String title = cache.get(url).getValue();
                LinkInfo linkInfo = new LinkInfo(url, title);
                links.add(linkInfo);
            } else {
                try {
                    String title = getPageTitle(url);
                    LinkInfo linkInfo = new LinkInfo(url, title);
                    links.add(linkInfo);
                    cache.put(url, new CacheData<>(title, System.currentTimeMillis(), CACHE_TIMEOUT, CACHE_TIME_UNIT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return links;
    }

    private String getPageTitle(String url) throws IOException {
        String htmlContent = linkRepository.fetchWebContent(url);
        return extractTitle(htmlContent);
    }

    private String extractTitle(String htmlContent) {
        Pattern pattern = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = pattern.matcher(htmlContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return EMPTY_TITLE;
    }
}
