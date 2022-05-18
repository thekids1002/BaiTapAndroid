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

import com.squareup.picasso.Picasso;

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
        population.setText(sdf.format(Double.parseDouble(country.getPopulation())));
        areaInSqKm.setText(sdf.format(Double.parseDouble(country.getAreaInSqKm())));
        capital.setText(country.getCapital());
        final String MAPURL = "http://img.geonames.org/img/country/250/" + country.getCountryMap().toUpperCase() + ".png";
        Picasso.get().load(MAPURL).placeholder(R.drawable.progress_animation).into(countryMap);
    }

    

}
