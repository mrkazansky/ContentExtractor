package com.mrkaz.sdk.data.extractor;

import java.util.List;

public abstract class Extractor<T> {
    public abstract List<T> extract(String input);
}
