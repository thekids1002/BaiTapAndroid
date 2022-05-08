package com.example.baitap2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapter.CountryAdapter;
import Model.Country;

public class MainActivity extends AppCompatActivity {
    private CountryAdapter countryAdapter;
    private Country country;
    public static ArrayList<Country> countries = new ArrayList<Country>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvents();

    }

    private void addEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Country country = countries.get(i);
                Intent intent = new Intent(MainActivity.this, InfoCountryActivity.class);
                intent.putExtra("CountryInfo", country);
                startActivity(intent);
            }
        });
    }

    private void addControl() {

        listView = findViewById(R.id.lvquocgia);
        countryAdapter = new CountryAdapter(MainActivity.this, R.layout.listview_custom, countries);
        ContryTask contacTask = new ContryTask();
        contacTask.execute();
        listView.setAdapter(countryAdapter);

    }


    class ContryTask extends AsyncTask<Void, Void, ArrayList<Country>> {
        private ProgressDialog dialog;

        public ContryTask() {
            dialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected ArrayList<Country> doInBackground(Void... voids) {
            ArrayList<Country> ds = new ArrayList<Country>();
            try {
                URL url = new URL("http://api.geonames.org/countryInfoJSON?username=btandroid2");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                Log.e("Log ", builder.toString());
                JSONObject result = new JSONObject(builder.toString());
                JSONArray jsonArray = result.getJSONArray("geonames");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Country country = new Country();
                    if (jsonObject.has("countryName")) {
                        country.setCountry_name(jsonObject.getString("countryName"));
                    }
                    if (jsonObject.has("population")) {
                        country.setPopulation(jsonObject.getString("population"));
                    }
                    if (jsonObject.has("areaInSqKm")) {
                        country.setAreaInSqKm(jsonObject.getString("areaInSqKm"));
                    }
                    if (jsonObject.has("countryCode")) {
                        String country_code = jsonObject.getString("countryCode").toLowerCase();
                        String link_image = "http://img.geonames.org/flags/x/" + country_code + ".gif";
                        country.setImage(link_image);
                        country.setCountryMap(country_code);
                    }
                    if (jsonObject.has("capital")) {
                        String capital = jsonObject.getString("capital");
                        country.setCapital(capital);
                    }
                    System.out.println(country.toString());
                    ds.add(country);
                }
            } catch (Exception e) {
                Log.e("Loi", e.toString());

            }
            return ds;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            countryAdapter.clear();
            dialog.setMessage("Đang tải dữ liệu vui lòng chờ");
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Country> countries) {
            super.onPostExecute(countries);
            countryAdapter.clear();
            countryAdapter.addAll(countries);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}