package com.redfin.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redfin.model.SocrataFoodTruckData;
import com.redfin.util.DateTimeConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class SocrataClient {

    private static final String AUTH_HEADER = "x-app-token";
    private static final String APP_TOKEN = "G65AdHltDAVg6yDEIPpHxy7so";
    private static final String DATA_SOURCE_URL = "https://data.sfgov.org/resource/jjew-r69b.json?dayorder=%s";

    public List<SocrataFoodTruckData> getCurrentData() {
        try {
            int dayOfTheWeek = DateTimeConverter.dayOfWeekNumber();
            String url = String.format(DATA_SOURCE_URL, dayOfTheWeek);
            String result = fetchData(url);
            return convertToSocrataData(result);
        } catch (Exception ex) {
            System.out.println("Could not fetch data");
            ex.printStackTrace();
        }

        return Collections.emptyList();
    }

    private String fetchData(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty(AUTH_HEADER, APP_TOKEN);
        String result = getData(connection);
        connection.disconnect();
        return result;
    }

    private String getData(HttpURLConnection connection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }

    private List<SocrataFoodTruckData> convertToSocrataData(String data) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(data, new TypeReference<List<SocrataFoodTruckData>>() {});
    }
}
