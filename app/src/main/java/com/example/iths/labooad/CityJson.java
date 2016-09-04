package com.example.iths.labooad;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by iths on 2016-09-03.
 */
public class CityJson {
    private final String name;
    private final int habitants;

    private CityJson(CityJsonBuilder cityJsonBuilder) {

        if (cityJsonBuilder.name == null || cityJsonBuilder.habitants == null){
            throw new IllegalStateException("Values not set correctly");
        }

        name = cityJsonBuilder.name;
        habitants = cityJsonBuilder.habitants;

    }

    public String getCityJsonStringToPost() {
        String tempString = null;

        JSONObject jsonCity = new JSONObject();

        try {

            jsonCity.put("name", name);
            jsonCity.put("population", habitants);
            tempString = jsonCity.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tempString;
    }




    public static final class CityJsonBuilder {
        private String name;
        private Integer habitants;

        public CityJsonBuilder setName(String name) {
            this.name = name;
            return this;

        }

        public CityJsonBuilder setHabitants(Integer habitants) {
            this.habitants = new Integer(habitants);
            return this;
        }

        public CityJson build() {
            return new CityJson(this);
        }

    }

}
