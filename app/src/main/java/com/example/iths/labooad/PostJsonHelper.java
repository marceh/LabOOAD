package com.example.iths.labooad;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by iths on 2016-09-02.
 */
public class PostJsonHelper extends AsyncTask<String, Void, Void> implements Command{
    private static final String TAG = "PostJsonHelper";
    private Activity activity;
    private String cityJsonString;
    private CallbackExecutor callbackExecutor;

    public PostJsonHelper(Activity activity, String cityJsonString, CallbackExecutor callbackExecutor) {
        this.activity = activity;
        this.cityJsonString = cityJsonString;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(activity, "Uploading", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            URL url = new URL("http://cities.jonkri.se/0.0.0/cities");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(cityJsonString);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            displayWhatReturned(httpURLConnection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void displayWhatReturned(HttpURLConnection httpURLConnection) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String tempLine = null;
                while ((tempLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tempLine + "\n");
                }
                bufferedReader.close();
                Log.d(TAG, stringBuilder.toString());
            } else {
                Log.d(TAG, "Error: " + httpURLConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        Toast.makeText(activity, "Added city to API", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void executeCommand() {
        callbackExecutor.executeCallbackNotifyListArrayHasChanged();
    }
}
