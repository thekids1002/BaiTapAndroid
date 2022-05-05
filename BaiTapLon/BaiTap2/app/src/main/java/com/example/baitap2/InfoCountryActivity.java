package com.example.baitap2;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import Model.Country;

public class InfoCountryActivity extends AppCompatActivity {
    TextView population, areaInSqKm, capital;
    ImageView countryMap;
    private Country country;
    Bitmap bitmap;
    DecimalFormat sdf = new DecimalFormat("###,###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_country);
        addControl();
        addEvent();
    }

    public void addControl() {
        population = findViewById(R.id.population);
        areaInSqKm = findViewById(R.id.areaInSqKm);
        capital = findViewById(R.id.capital);
        countryMap = findViewById(R.id.countryMaps);
    }

    public void addEvent() {
        country = (Country) getIntent().getSerializableExtra("CountryInfo");
        getMapCountryTask getMapCountryTask = new getMapCountryTask();
        getMapCountryTask.execute();
    }

    class getMapCountryTask extends AsyncTask<Void, Void, Bitmap> {
        private ProgressDialog dialog;

        public getMapCountryTask() {
            dialog = new ProgressDialog(InfoCountryActivity.this);
        }


        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                final String MAPURL = "https://img.geonames.org/img/country/250/" + country.getCountryMap().toUpperCase() + ".png";
                Log.e("Err", "doInBackground: " + MAPURL);
                URL url = new URL(MAPURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Đang tải dữ liệu vui lòng chờ");
            dialog.show();
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            population.setText(sdf.format(Double.parseDouble(country.getPopulation())));
            areaInSqKm.setText(sdf.format(Double.parseDouble(country.getAreaInSqKm())));
            capital.setText(country.getCapital());
            countryMap.setImageBitmap(bitmap);
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