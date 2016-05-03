package com.test.websocket.auth.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by timur on 03.05.16.
 */
public class StatisticsCollector {
    private static StatisticsCollector instance = new StatisticsCollector();

    private List<String> serverResponses = Collections.synchronizedList(new ArrayList<String>());

    public static StatisticsCollector getInstance() {
        return instance;
    }

    public List<String> getServerResponses() {
        return serverResponses;
    }

    public void addServerResponse(String text) {
        serverResponses.add(text);
    }

    public String getLastServerResponse() {
        return serverResponses.isEmpty() ? null : serverResponses.get(serverResponses.size() - 1);
    }
}
