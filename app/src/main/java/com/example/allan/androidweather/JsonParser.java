package com.example.allan.androidweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Custom JSON parser made to list all available Namespaces.
 */


public class JsonParser {

    public Meteo parse(String feed) {
        Meteo meteo = null;

        try {
            double latitude = 0.0;
            double longitude = 0.0;
            double currentTemp = 0.0;
            double maxTemp = 0.0;
            double minTemp = 0.0;
            String description = null;
            String icone = null;

            JSONObject root = new JSONObject(feed);

            if(root.has("coord")) {
                JSONObject coord = root.getJSONObject("coord");

                longitude = coord.getDouble("lon");
                latitude = coord.getDouble("lat");
            }

            if(root.has("weather")) {
                JSONArray weatherArray = root.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                description = weatherObject.getString("description");
                icone = weatherObject.getString("icon");
            }

            if(root.has("main")) {
                JSONObject main = root.getJSONObject("main");

                currentTemp = main.getDouble("temp");
                maxTemp = main.getDouble("temp_min");
                minTemp = main.getDouble("temp_max");
            }


            meteo = new Meteo(latitude, longitude, currentTemp, maxTemp, minTemp, description, icone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return meteo;
    }
}
