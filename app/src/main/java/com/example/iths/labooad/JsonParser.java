package com.example.iths.labooad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by iths on 2016-09-01.
 */
public class JsonParser {

    public static ArrayList<City> parseJsonArrayToCityArrayList(JSONArray jsonArray) {
        ArrayList<City> cities = new ArrayList<City>();

        if (jsonArray != null) {
            int length = jsonArray.length();
            for (int i=0 ; i < length ; i++){

                try {
                    JSONObject tempObject = jsonArray.getJSONObject(i);
                    City tempCity = new City((String) tempObject.get("name"), (int) tempObject.get("population"));
                    cities.add(tempCity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return cities;
    }

}
