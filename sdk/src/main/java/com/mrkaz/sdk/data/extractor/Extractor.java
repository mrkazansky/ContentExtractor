package com.mrkaz.sdk.data.extractor;

import java.util.List;

public interface  Extractor<T> {
    List<T> extract(String input);
}
