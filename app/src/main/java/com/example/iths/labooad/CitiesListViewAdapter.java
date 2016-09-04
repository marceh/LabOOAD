package com.example.iths.labooad;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iths on 2016-09-01.
 */
public class CitiesListViewAdapter extends BaseAdapter {

    private ArrayList<City> cities;
    private LayoutInflater inflater;

    public CitiesListViewAdapter(Activity activity, ArrayList<City> cities) {
        this.cities = cities;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.cities.size();
    }

    @Override
    public Object getItem(int position) {
        return this.cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView == null){
            view = this.inflater.inflate(R.layout.city_layout, parent, false);
        } else {
            view = convertView;
        }

        TextView textViewCityName = (TextView) view.findViewById(R.id.textViewCityName);
        textViewCityName.setText(this.cities.get(position).getName());

        TextView textViewCityHabitants = (TextView) view.findViewById(R.id.textViewCityHabitants);
        textViewCityHabitants.setText(Integer.toString(this.cities.get(position).getHabitants()));

        return view;
    }
}
