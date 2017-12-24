package com.example.android.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpcomingWeek extends AppCompatActivity {
    private static TextView displayForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_week);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayForecast = (TextView)findViewById(R.id.Forecast);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        new JSONTask().execute("https://api.darksky.net/forecast/b6da387c37e2586c221d43a4a7ecdc0b/30.2672,-97.7431");
    }

    public static class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            //Get request
            try{
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine())!= null){
                    buffer.append(line);
                }


                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //Json parsing

            try {
                JSONObject dataObj = new JSONObject(result);
                JSONObject dailyObj = dataObj.getJSONObject("daily");
                JSONArray dailyArr = dailyObj.getJSONArray("data");
                ArrayList<String> buffer = new ArrayList<>();

                for(int i = 0;i<dailyArr.length();i++) {
                    JSONObject finalObj = dailyArr.getJSONObject(i);
                    String tempHigh = finalObj.getString("temperatureHigh");
                    String tempLow = finalObj.getString("temperatureLow");
                    buffer.add(tempHigh);
                    buffer.add(tempLow);
                }

                displayForecast.setText("Day 1 High: " + buffer.get(0) + "\n" + "Day 1 Low: " + buffer.get(1) + "\n");

                for(int i = 1; i < buffer.size()/2;i++){
                    displayForecast.append("Day " + (i+1) + " high: " + buffer.get(i+i) + "\n");
                    displayForecast.append("Day " + (i+1) + " lows: " + buffer.get(i+i+1) + "\n");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
