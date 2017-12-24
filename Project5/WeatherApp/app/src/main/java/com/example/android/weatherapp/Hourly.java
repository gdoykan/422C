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

public class Hourly extends AppCompatActivity {

    private static TextView displayHourly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayHourly = (TextView)findViewById(R.id.displayHourly);

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

                //prase 5 objects of hourly JSON array

                JSONObject hourlyObj = dataObj.getJSONObject("hourly");
                JSONArray hourlyArr = hourlyObj.getJSONArray("data");


                JSONObject finalObj = hourlyArr.getJSONObject(0);
                String hour1 = finalObj.getString("temperature");
                finalObj = hourlyArr.getJSONObject(1);
                String hour2 = finalObj.getString("temperature");
                finalObj = hourlyArr.getJSONObject(2);
                String hour3 = finalObj.getString("temperature");
                finalObj = hourlyArr.getJSONObject(3);
                String hour4 = finalObj.getString("temperature");
                finalObj = hourlyArr.getJSONObject(4);
                String hour5 = finalObj.getString("temperature");


                displayHourly.setText("1 hour: " + hour1 + "\n"
                        + "2 hours: " + hour2 + "\n"
                        + "3 hours: " + hour3 + "\n"
                        + "4 hours: " + hour4 + "\n"
                        + "5 hours: " + hour5 + "\n"
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
