package com.mrkaz.sdk.data.extractor.implermentation;

import com.mrkaz.sdk.data.extractor.Extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MentionExtractor implements Extractor<String> {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\w+)");

    @Override
    public List<String> extract(String input) {
        List<String> mentions = new ArrayList<>();
        if (input == null)
            return mentions;
        Matcher matcher = MENTION_PATTERN.matcher(input);
        while (matcher.find()) {
            String mention = matcher.group();
            mentions.add(mention);
        }
        return mentions;
    }
}
