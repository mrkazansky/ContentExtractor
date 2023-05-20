package com.mrkaz.sdk.data.network.implementation;

import com.mrkaz.sdk.data.network.NetworkService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetworkServiceImpl implements NetworkService {

    @Override
    public String fetchWebContent(String webUrl) throws IOException {
        URL url = new URL(webUrl);
        URLConnection connection = url.openConnection();
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        }
        return contentBuilder.toString();
    }
}
