package com.baitapnhom.baitap2;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import Model.Country;
import Util.CustomProgressDialog;

public class InfoCountryActivity extends AppCompatActivity {
    TextView population, areaInSqKm, capital, MapName;
    ImageView countryMap;
    private Country country;
    Bitmap bitmap;
    DecimalFormat sdf = new DecimalFormat("###,###.###");
    TableLayout tableLayout;

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
        tableLayout = findViewById(R.id.table);
        MapName = findViewById(R.id.MapName);
    }

    public void addEvent() {
        Intent intent = getIntent();
        String getCountry_name = intent.getStringExtra("getCountry_name");
        String getPopulation = intent.getStringExtra("getPopulation");
        String getAreaInSqKm = intent.getStringExtra("getAreaInSqKm");
        String getCapital = intent.getStringExtra("getCapital");
        String getCountryMap = intent.getStringExtra("getCountryMap");
        country = new Country();
        country.setCountryMap(getCountryMap);
        country.setCapital(getCapital);
        country.setPopulation(getPopulation);
        country.setAreaInSqKm(getAreaInSqKm);
        country.setCountry_name(getCountry_name);
        getMapCountryTask getMapCountryTask = new getMapCountryTask();
        getMapCountryTask.execute();
    }

    class getMapCountryTask extends AsyncTask<Void, Void, Bitmap> {
//        private ProgressDialog dialog;
        private CustomProgressDialog lottie ;
        public getMapCountryTask() {
//            dialog = new ProgressDialog(InfoCountryActivity.this);
            lottie  = new CustomProgressDialog(InfoCountryActivity.this);
        }


        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                final String MAPURL = "http://img.geonames.org/img/country/250/" + country.getCountryMap().toUpperCase() + ".png";
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
            lottie.show();
            MapName.setVisibility(View.GONE);
            tableLayout.setVisibility(View.GONE);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            population.setText(sdf.format(Double.parseDouble(country.getPopulation())) + " người");
            areaInSqKm.setText(sdf.format(Double.parseDouble(country.getAreaInSqKm())) + " Km2");
            capital.setText(country.getCapital());
            countryMap.setImageBitmap(bitmap);
            MapName.setText("Bản đồ quốc gia " + country.getCountry_name());
            if (lottie.isShowing()) {
                lottie.dismiss();
                tableLayout.setVisibility(View.VISIBLE);
                MapName.setVisibility(View.VISIBLE);
            }
        }
    }

}