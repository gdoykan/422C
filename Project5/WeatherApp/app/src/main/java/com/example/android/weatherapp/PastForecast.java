package com.example.android.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PastForecast extends AppCompatActivity {

    private static String userStr ="";
    private static String URL1 = "https://api.darksky.net/forecast/b6da387c37e2586c221d43a4a7ecdc0b/30.2672,-97.7431,";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button performQuery = (Button) findViewById(R.id.submit);
        final EditText result  = (EditText)findViewById(R.id.getInput);

        performQuery.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                        {
                            userStr = result.getText().toString();
                            URL1 = URL1 + " " + userStr + "T00:00:00";
                            new JSONTask().execute();
                            Toast.makeText(getBaseContext(), "Button WORKS!" , Toast.LENGTH_SHORT ).show();
                        }
                });

    }

    public class JSONTask extends AsyncTask<Void, Void, String>
    {
        TextView display = (TextView)findViewById(R.id.displayTemp);

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL(URL1);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder data = new StringBuilder();
                String temp;

                while((temp = reader.readLine()) != null)
                {
                    data.append(temp).append("\n");
                }

                reader.close();

                String extracted = data.toString();

                return extracted;

            }
            catch (Exception e)
            {
                return null;
            }

        }


        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            //display.setText(s);

            try
            {
                JSONObject data = new JSONObject(s);
                String temp = data.getJSONObject("currently").getString("temperature");
                display.setText("Temperature on that day was: " + temp);
                //display.setText(URL1);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }



}





