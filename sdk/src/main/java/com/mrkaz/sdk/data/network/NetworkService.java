package com.mrkaz.sdk.data.network;

import java.io.IOException;

public interface NetworkService {
    String fetchWebContent(String url) throws IOException;
}
