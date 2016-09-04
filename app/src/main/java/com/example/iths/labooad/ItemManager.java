package com.example.iths.labooad;

import java.util.ArrayList;

/**
 * Created by iths on 2016-09-01.
 */
public class ItemManager{

    private static ItemManager instance = null;
    private ArrayList<City> cities = new ArrayList();

    private ItemManager(){}

    public static ItemManager getInstance(){

        if (instance == null){
            instance = new ItemManager();
        }

        return instance;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public void addCityToCities(City city) {
        cities.add(city);
    }

}
