package com.example.iths.labooad;

/**
 * Created by iths on 2016-09-01.
 */
public class City {
    
    private String name;
    private int habitants;

    public City(String name, int habitants) {
        this.name = name;
        this.habitants = habitants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHabitants() {
        return habitants;
    }

    public void setHabitants(int habitants) {
        this.habitants = habitants;
    }
}
