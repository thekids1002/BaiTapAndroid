package com.baitapnhom.baitap3;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import Adapter.CustomSpinnerAdapter;
import Model.Currency;
import Model.HistoryCurrency;
import Utils.CustomProgressDialog;
import Utils.MyConfig;
import Utils.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DecimalFormat sdf = new DecimalFormat("###,###,###.###");
    EditText txtCurrencyFrom, txtCurrencyTo;
  //  Spinner spn_from, spn_to;
    TextView CurrencyCodeFrom, CurrencyCodeTo, txtcurrency;
    ImageButton btnChangeCurrent, btnChangeCurrent2;
    Currency currency1, currency2;
    Button btnConver, btnHistory;
    CustomSpinnerAdapter adapter;
    ArrayList<Currency> currencyArrayList = new ArrayList<>();
    SearchableSpinner spn_from,spn_to;

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
                    currency1 = (Currency) adapterView.getSelectedItem();
                    CurrencyCodeFrom.setText(currencyArrayList.get(i).getCurrencyCode());

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
                    currency2 = (Currency) adapterView.getSelectedItem();
                    CurrencyCodeTo.setText(currencyArrayList.get(i).getCurrencyCode());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChangeCurrent.setOnClickListener(view -> {
            int index1 = spn_from.getSelectedItemPosition();
            int index2 = spn_to.getSelectedItemPosition();
            spn_from.setSelection(index2);
            spn_to.setSelection(index1);
        });

        btnConver.setOnClickListener(view -> {
            ConverterAsynctask converter = new ConverterAsynctask();
            converter.execute();
        });

        btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, History_Activity.class);
            startActivity(intent);
        });
    }


    private void addControl() {
        CurrencyAsyntask currencyAsyntask = new CurrencyAsyntask();
        currencyAsyntask.execute();
        txtCurrencyTo = findViewById(R.id.txtCurrencyTo);
        txtCurrencyFrom = findViewById(R.id.txtCurrencyFrom);
        spn_from = findViewById(R.id.spn_from);
        spn_to = findViewById(R.id.spn_to);
        btnChangeCurrent = findViewById(R.id.btnChangeCurrent);
        btnChangeCurrent2 = findViewById(R.id.btnChangeCurrent2);
        adapter = new CustomSpinnerAdapter(MainActivity.this, R.layout.custom_spinner_items, currencyArrayList);
        spn_from.setAdapter(adapter);
        spn_to.setAdapter(adapter);
        spn_from.setTitle("Chọn quốc gia");
        spn_to.setTitle("Chọn quốc gia");
        spn_from.setPositiveButton("OK");
        spn_to.setPositiveButton("OK");
        txtcurrency = findViewById(R.id.currency);
        CurrencyCodeFrom = findViewById(R.id.CurrencyCodeFrom);
        CurrencyCodeTo = findViewById(R.id.CurrencyCodeTo);
        btnConver = findViewById(R.id.btnConvert);
        btnHistory = findViewById(R.id.btnHistory);
    }

    private void saveHistory(HistoryCurrency his) {
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        databaseHelper.addCurrency(his);
    }

    class ConverterAsynctask extends AsyncTask<String, Void, String> {
        CustomProgressDialog progressDialog = new CustomProgressDialog(MainActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressDialog.show();
                if (txtCurrencyFrom.getText().toString().isEmpty()) {
                    txtCurrencyTo.setText("");
                    progressDialog.dismiss();
                    builder.setTitle("Không được để trống").setMessage("Bạn vui lòng nhập giá trị cần đổi !!!");
                    builder.setCancelable(true);
                    builder.show();
                }
                if (currency1 == null || currency2 == null) {
                    progressDialog.dismiss();
                    builder.setTitle("Quốc gia không được để trống").setMessage("Bạn vui lòng chọn quốc gia cần đổi !!!");
                    builder.setCancelable(true);
                    builder.show();
                }
                if (currency1.getCurrencyCode().equals(currency2.getCurrencyCode())) {
                    progressDialog.dismiss();
                    String txtValue = txtCurrencyFrom.getText().toString();
                    txtCurrencyTo.setText(txtValue);
                    HistoryCurrency his = new HistoryCurrency();
                    his.setSpn_from(currency1.getCurrencyCode());
                    his.setSpn_to(currency2.getCurrencyCode());
                    his.setValue_from(sdf.format(Float.parseFloat(txtValue)));
                    his.setValue_to(txtValue);
                    saveHistory(his);
                }
            }
            catch (Exception e){
                progressDialog.dismiss();
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                currency1 = currencyArrayList.get(spn_from.getSelectedItemPosition());
                currency2 = currencyArrayList.get(spn_to.getSelectedItemPosition());
                String currency_name_1 = currency1.getCurrencyCode();
                String currency_name_2 = currency2.getCurrencyCode();
                String URL = MyConfig.getAPIConverter(currency_name_1,currency_name_2);
                URL url = new URL(URL.toLowerCase());
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                XMLDOMParser parser = new XMLDOMParser();
                Document document = parser.getDocument(s);
                NodeList nodeList = document.getElementsByTagName("item");
                String result = "";
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    NodeList DescriptionNode = element.getElementsByTagName("description");
                    Element DescriptionEle = (Element) DescriptionNode.item(i);
                    result = Html.fromHtml(DescriptionEle.getFirstChild().getNodeValue().trim()).toString();
                    result = result.replaceAll(currency1.getCurrencyCode(),"");
                    result = result.replaceAll(currency2.getCurrencyCode(),"");
                }
                String[] arr = result.split("\n");
                String currency = arr[0];
                txtcurrency.setText(currency);
                String[] arrcurency = currency.split("=");
                Double input = Double.parseDouble(txtCurrencyFrom.getText().toString().trim());
                Double rate = Double.parseDouble(arrcurency[1].trim());
                Double output =  (input * rate);
                if( rate < 1){
                    currency = arr[1];
                    arrcurency = currency.split("=");
                    rate = Double.parseDouble(arrcurency[1].trim());
                    output =  (input / rate) ;
                }
                txtCurrencyTo.setText(sdf.format(output));
                HistoryCurrency his = new HistoryCurrency();
                his.setSpn_from(currency1.getCurrencyCode());
                his.setSpn_to(currency2.getCurrencyCode());
                his.setValue_from(sdf.format(input));
                his.setValue_to(sdf.format(output));
                if(!String.valueOf(output).isEmpty()){
                    saveHistory(his);
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                builder.setTitle("Có lỗi xảy ra").setMessage("Bạn vui lòng chọn quốc gia cần đổi !!!");
                builder.setCancelable(true);
                builder.show();
                e.printStackTrace();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }

    class CurrencyAsyntask extends AsyncTask<Void, Void, ArrayList<Currency>> {
        private CustomProgressDialog progressDialog = new CustomProgressDialog(MainActivity.this);
        @Override
        protected void onPostExecute(ArrayList<Currency> currencie) {
            super.onPostExecute(currencie);
           try {
               currencyArrayList.clear();
               currencyArrayList.addAll(currencie);
               adapter.notifyDataSetChanged();
               spn_from.setSelection(0);
               spn_to.setSelection(0);
               if (progressDialog.isShowing()) {
                   progressDialog.dismiss();
               }
           }
           catch (Exception e){
                e.printStackTrace();
           }
        }

        @Override
        protected void onPreExecute() {
            currencyArrayList.clear();
            progressDialog.show();
        }

        @Override
        protected ArrayList<Currency> doInBackground(Void... voids) {
            ArrayList<Currency> currenciesList = new ArrayList<>();
            try {
                URL url = new URL(MyConfig.GEOSNAME_API);
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
                    if (jsonObject.has("countryName")) {
                        currency.setCountryName(jsonObject.getString("countryName"));
                    }
                    currenciesList.add(currency);
                }
                return currenciesList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}