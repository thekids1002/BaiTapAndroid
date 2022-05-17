package com.baitapnhom.baitap2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapter.CountryAdapter;
import Model.Country;
import Util.CustomProgressDialog;

public class MainActivity extends AppCompatActivity {
    private CountryAdapter countryAdapter;
    public static ArrayList<Country> countries = new ArrayList<>();
    public static ArrayList<Country> lazy_load_countries = new ArrayList<>();
    public static ArrayList<Country> temp = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private boolean isFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvents();

    }


    private void addControl() {
        listView = findViewById(R.id.lvquocgia);
        countryAdapter = new CountryAdapter(MainActivity.this, lazy_load_countries);
        ContryTask contacTask = new ContryTask();
        contacTask.execute();
        listView.setAdapter(countryAdapter);
        searchView = findViewById(R.id.search_bar);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        listView.setTextFilterEnabled(true);

    }

    private void addEvents() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Country country = lazy_load_countries.get(i);
            Intent intent = new Intent(MainActivity.this, InfoCountryActivity.class);
            intent.putExtra("getCountry_name", country.getCountry_name());
            intent.putExtra("getCountryMap", country.getCountryMap());
            intent.putExtra("getCapital", country.getCapital());
            intent.putExtra("getAreaInSqKm", country.getAreaInSqKm());
            intent.putExtra("getPopulation", country.getPopulation());
            startActivity(intent);
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && !isFilter) {
                    if (countries.isEmpty()) {
                        return;
                    }
                    try {
                        for (int i = totalItemCount; i < totalItemCount + 7; ++i) {
                            if (totalItemCount == (countries.size() - 1)) {
                                break;
                            }
                            Country country = countries.get(i);
                            System.out.println("Cái gì đấy " + i + country.getCountry_name());
                            lazy_load_countries.add(country);
                            for (Country country1 : lazy_load_countries) {
                                System.out.println("LazyLoad" + country1.getCountry_name());
                            }
                        }
                        countryAdapter.notifyDataSetChanged();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if(TextUtils.isEmpty(newText) || newText.length() == 0 || newText.equals("")){
//                    isSearch = false;
//                    for (Country country : lazy_load_countries){
//                        Log.e("Tag",country.getCountry_name());
//                    }
//                    countryAdapter = new CountryAdapter(MainActivity.this,R.layout.listview_custom,lazy_load_countries);
//                    listView.setAdapter(countryAdapter);
//                }
//                else{
//                    isSearch = true;
//                    countryAdapter.clear();
//                    for (int i = 0 ; i < countries.size() ; i++){
//                        if(countries.get(i).getCountry_name().toLowerCase().contains(newText.toLowerCase())){
//                            countryAdapter.add(countries.get(i));
//                        }
//                    }
//
//                    //
//                }
//                countryAdapter.notifyDataSetChanged();
                if (newText.isEmpty()) {
                    listView.clearTextFilter();
                    isFilter = false;
                    return false;
                } else {
                    isFilter = true;
                    countryAdapter.getFilter().filter(newText);
                    return false;
                }


            }
        });
    }

    class ContryTask extends AsyncTask<Void, Void, ArrayList<Country>> {
        // private ProgressDialog dialog;
        private CustomProgressDialog lottie;

        public ContryTask() {
            //  dialog = new ProgressDialog(MainActivity.this);
            lottie = new CustomProgressDialog(MainActivity.this);
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
            countries.clear();
//            dialog.setMessage("Đang tải dữ liệu vui lòng chờ");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
            lottie.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Country> countriesList) {
//            countryAdapter.clear();
//            countryAdapter.addAll(countries);
            countries.addAll(countriesList);//256
            if (lottie.isShowing()) {
                lottie.dismiss();
            }
            for (int i = 0; i < 6; i++) {
                Country countrySelected = countries.get(i);
                Log.e("TAGGGGGGGGGGGGG", countrySelected.toString());
                lazy_load_countries.add(countrySelected);
            }
            countryAdapter.notifyDataSetChanged();
        }

    }
}