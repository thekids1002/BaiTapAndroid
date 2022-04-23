package com.baitapnhom.baitap3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Model.Currency;

public class MainActivity extends AppCompatActivity {
    EditText txtCurrencyFrom, txtCurrencyTo;
    ImageView ImageCountryFrom, ImageCountryTo;
    Spinner spn_from, spn_to;
    TextView CurrencyCodeFrom, CurrencyCodeTo, txtcurrency;
    ImageButton btnChangeCurrent, btnChangeCurrent2;
    private Currency currency1, currency2;
    Button btnConver;
    public static ArrayList<Currency> currencies = new ArrayList<Currency>();
    public static ArrayList<String> currStrings = new ArrayList<String>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addControl();
        addEvent();

    }

    private void addEvent() {
        spn_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (currencies.isEmpty()) {
                        return;
                    }
               //     Toast.makeText(getApplicationContext(), "ImageCountryFrom", Toast.LENGTH_SHORT).show();
                    String url = "https://img.geonames.org/flags/m/" + currencies.get(i).getCountryCode().toLowerCase() + ".png";
                    Picasso.get().load(url).into(ImageCountryFrom);
                    CurrencyCodeFrom.setText(currencies.get(i).getCurrencyCode());
                    currency1 = new Currency();
                    currency1 = currencies.get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (currencies.isEmpty()) {
                        return;
                    }
             //       Toast.makeText(getApplicationContext(), "ImageCountryTo", Toast.LENGTH_SHORT).show();
                    String url = "https://img.geonames.org/flags/m/" + currencies.get(i).getCountryCode().toLowerCase() + ".png";
                    Picasso.get().load(url).into(ImageCountryTo);
                    CurrencyCodeTo.setText(currencies.get(i).getCurrencyCode());
                    currency2 = new Currency();
                    currency2 = currencies.get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChangeCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index1 = 0;
                int index2 = 0;
                for (int i = 0; i < currencies.size(); i++) {
                    if (currencies.get(i).getCountryCode().toLowerCase().equals(currency1.getCountryCode().toLowerCase())) {
                        index1 = i;
                        Log.e("CCCCCCCCCCC", index1 + "");

                    }
                    if (currencies.get(i).getCountryCode().toLowerCase().equals(currency2.getCountryCode().toLowerCase())) {
                        index2 = i;
                        Log.e("CCCCCCCCCCC", index2 + "");
                    }
                }
                spn_from.setSelection(index2);
                spn_to.setSelection(index1);
            }
        });

        btnConver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Converter converter = new Converter();
                converter.execute();
            }
        });
    }

    ArrayAdapter<String> adapter;

    private void addControl() {
        CurrencyAsyntask currencyAsyntask = new CurrencyAsyntask();
        currencyAsyntask.execute();
       // Converter converter = new Converter();
      //  converter.execute();
        txtCurrencyTo = findViewById(R.id.txtCurrencyTo);
        txtCurrencyFrom = findViewById(R.id.txtCurrencyFrom);
        ImageCountryFrom = findViewById(R.id.ImageCountryFrom);
        ImageCountryTo = findViewById(R.id.ImageCountryTo);
        spn_from = findViewById(R.id.spn_from);
        spn_to = findViewById(R.id.spn_to);
        btnChangeCurrent = findViewById(R.id.btnChangeCurrent);
        btnChangeCurrent2 = findViewById(R.id.btnChangeCurrent2);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_from.setAdapter(adapter);
        spn_to.setAdapter(adapter);
        txtcurrency = findViewById(R.id.currency);
        CurrencyCodeFrom = findViewById(R.id.CurrencyCodeFrom);
        CurrencyCodeTo = findViewById(R.id.CurrencyCodeTo);
        btnConver = findViewById(R.id.btnConvert);


    }

    class Converter extends AsyncTask<String, Void, String> {

    public Converter(){
        dialog = new ProgressDialog(MainActivity.this);
    }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang tải dữ liệu vui lòng chờ");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder content = new StringBuilder();
            try {
                String currency_name_1 = currency1.getCurrencyCode();
                String currency_name_2 = currency2.getCurrencyCode();
                String URL = "https://"+currency_name_1+".fxexchangerate.com/"+currency_name_2+".xml";
                URL url = new URL(URL.toLowerCase());
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return content.toString();
        }
        public String formatDecimal(float number) {
            float epsilon = 0.004f; // 4 tenths of a cent
            if (Math.abs(Math.round(number) - number) < epsilon) {
                return String.format("%10.0f", number); // sdb
            } else {
                return String.format("%10.2f", number); // dj_segfault
            }
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                if(txtCurrencyFrom.getText().toString().isEmpty() || txtCurrencyFrom.getText().toString() == ""){
                    txtCurrencyTo.setText("");
                    dialog.dismiss();
                    return;
                }
                if(currency1 == null || currency2 == null){
                    dialog.dismiss();
                    return;
                }
                XMLDOMParser parser = new XMLDOMParser();
                Document document = parser.getDocument(s);
                NodeList nodeList = document.getElementsByTagName("item");
                String tygia = "";
                for (int i = 0; i < 1; i++) {
                    Element element = (Element) nodeList.item(i);
                    NodeList DescriptionNode = element.getElementsByTagName("description");
                    Element DescriptionEle = (Element) DescriptionNode.item(i);
                    tygia = Html.fromHtml(DescriptionEle.getFirstChild().getNodeValue().trim()).toString();
                    Log.e("tygia", tygia);
                }
                String[] arr = tygia.split("\n");
                String currency = arr[0];
                txtcurrency.setText(currency);
                currency= currency.replace(currency1.getCurrencyCode(),"");
                currency = currency.replace(currency2.getCurrencyCode(),"");
                // tách ra 2 giá trị
                String[] arrcurency = currency.split("=");
                Float a = Float.parseFloat(arrcurency[0].trim());
                Float value = Float.parseFloat(txtCurrencyFrom.getText().toString().trim());
                Float b = Float.parseFloat(arrcurency[1].trim());
                Float c = value * b ;
                txtCurrencyTo.setText(formatDecimal(c)+"");
                super.onPostExecute(s);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
            catch (Exception e){
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Có lỗi xảy ra").setMessage("Vui lòng chọn lại quốc gia");
                builder.setCancelable(true);


            }
        }
    }

    class CurrencyAsyntask extends AsyncTask<Void, Void, ArrayList<Model.Currency>> {
        public CurrencyAsyntask(){
            dialog = new ProgressDialog(MainActivity.this);
        }
        @Override
        protected void onPostExecute(ArrayList<Model.Currency> currencie) {
            super.onPostExecute(currencie);
            currencies.clear();
            currencies.addAll(currencie);
            for (Currency currency : currencies
            ) {
                currStrings.add(currency.getCurrencyCode());
                System.out.println(currency.getCurrencyCode());
            }
            adapter.notifyDataSetChanged();
            spn_from.setSelection(0);
            spn_to.setSelection(0);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            currStrings.clear();
            super.onPreExecute();

            dialog.setMessage("Đang tải dữ liệu vui lòng chờ");
            dialog.show();
        }

        @Override
        protected ArrayList<Model.Currency> doInBackground(Void... voids) {
            ArrayList<Model.Currency> currencies = new ArrayList<Model.Currency>();
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
                JSONObject result = new JSONObject(builder.toString());
                JSONArray jsonArray = result.getJSONArray("geonames");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Model.Currency currency = new Model.Currency();
                    if (jsonObject.has("currencyCode")) {
                        currency.setCurrencyCode(jsonObject.getString("currencyCode"));
                    }
                    if (jsonObject.has("countryCode")) {
                        currency.setCountryCode(jsonObject.getString("countryCode"));
                    }
                    currencies.add(currency);
                }
                return currencies;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}