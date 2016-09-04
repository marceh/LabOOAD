package com.example.iths.labooad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by iths on 2016-09-02.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String NAME_KEY = "name";
    private static final String POP_KEY = "population";
    private static final String CITIES_TABLE = "Cities";
    private ItemManager itemManager;

    public SQLiteHelper(Context context) {
        super(context, "DB4OOAD", null, 1);
        this.itemManager = ItemManager.getInstance();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Cities (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "name VARCHAR(100) NOT NULL, ";
        sql += "population INTEGER";
        sql += " );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveCitiesToDbFromItemManager() {

        SQLiteDatabase db = getWritableDatabase();

        //1. Delete everything in table...
        deleteEveryCityInTable(db);

        //2. Loop through cities and set the table again...
        ArrayList<City> cities = itemManager.getCities();

        for (City city : cities) {
            addCityToDb(db, city);
        }

        db.close();

    }

    private void deleteEveryCityInTable(SQLiteDatabase db) {

        db.delete(CITIES_TABLE, null, null);

    }

    private void addCityToDb(SQLiteDatabase db, City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_KEY, city.getName());
        contentValues.put(POP_KEY, city.getHabitants());
        db.insert(CITIES_TABLE, null, contentValues);
    }

    public void getCitiesFromDbToItemManager() {

        SQLiteDatabase db = getReadableDatabase();

        //1. Create a cursor that collects everything from table...
        Cursor result = db.query(CITIES_TABLE, null, null, null, null, null, null, null);

        //2. Convert result to an ArrayList<City> and update ItemManager...
        itemManager.setCities(convertCursorResultTooArrayListOfCities(result));

    }

    private ArrayList<City> convertCursorResultTooArrayListOfCities(Cursor cursor) {

        ArrayList<City> tempCities = new ArrayList<City>();

        if (cursor.moveToFirst()) {

            do {

                City city = new City(cursor.getString(1), cursor.getInt(2));
                tempCities.add(city);

            } while (cursor.moveToNext());

            return tempCities;

        } else {

            return null;

        }
    }
}
