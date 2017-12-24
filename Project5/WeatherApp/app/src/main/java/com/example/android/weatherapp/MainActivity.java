package com.example.android.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static TextView tvData;
    private static TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData = (TextView) findViewById(R.id.tvData);
        display = (TextView) findViewById(R.id.displayWeather);


//        //for current conditions
//        Button btn = (Button) findViewById(R.id.current);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, currentConditions.class));
//            }
//        });

        //48 hour temperature average
        Button btn1 = (Button) findViewById(R.id.twoDayAvg);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoDayAvg.class));
            }
        });

        //hour by hour for next 5 hours
        Button btn2 = (Button) findViewById(R.id.hourByHour);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Hourly.class));
            }
        });

        //Upcoming week forecast
        Button btn3 = (Button) findViewById(R.id.futureTemp);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpcomingWeek.class));
            }
        });

        //Upcoming week forecast
        Button btn5 = (Button) findViewById(R.id.pastTemp);

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PastForecast.class));
                //Toast.makeText(getBaseContext(), "Button WORKS!" , Toast.LENGTH_SHORT ).show();
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
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
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
            JSONObject dataObj = null;
            try {
                dataObj = new JSONObject(result);
                JSONObject currentObj = dataObj.getJSONObject("currently");
                String temp = currentObj.getString("temperature");
                String humidity = currentObj.getString("humidity");
                String precip = currentObj.getString("precipProbability");
                String windSpeed = currentObj.getString("windSpeed");

                tvData.setText(temp + " °F");
                display.setText(" Humidity:  " + humidity + "\n"
                        + " Precipitation: " + precip + "%" + "\n"
                        + " Wind Speed: " + windSpeed  + " mph");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}

