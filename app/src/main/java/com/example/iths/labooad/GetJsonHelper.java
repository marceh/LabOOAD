package com.example.iths.labooad;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by iths on 2016-09-01.
 */
public class GetJsonHelper extends AsyncTask<String, Void, ArrayList<City>> implements Command {

    private static final String TAG = "GetJsonHelper";
    private MainActivity activity;
    private ItemManager itemManager;
    private CallbackExecutor callbackExecutor;

    public GetJsonHelper(MainActivity activity, CallbackExecutor callbackExecutor) {
        this.activity = activity;
        this.callbackExecutor = callbackExecutor;
        itemManager = ItemManager.getInstance();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(activity, "Loading Cities", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ArrayList<City> doInBackground(String... params) {
        String targetUrl = "http://cities.jonkri.se/0.0.0/cities";

        try {
            URL url = new URL(targetUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            JSONObject json = getJson(bufferedReader);

            ArrayList<City> cities = JsonParser.parseJsonArrayToCityArrayList(json.getJSONArray("items"));
            this.itemManager.setCities(cities);

        } catch (IOException | JSONException e) {
            Log.e("Exception", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<City>  cities) {
        super.onPostExecute(cities);
        this.executeCommand();
    }

    private JSONObject getJson(BufferedReader bufferedReader) throws IOException, JSONException{
        StringBuilder stringBuilder = new StringBuilder();
        int tempInt;
        while ((tempInt = bufferedReader.read()) != -1) {
            stringBuilder.append((char) tempInt);
        }

        return new JSONObject(stringBuilder.toString());
    }

    @Override
    public void executeCommand() {
        callbackExecutor.executeCallbackUpdateListWithValues();
    }
}
