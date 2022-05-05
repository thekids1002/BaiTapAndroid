package com.baitapnhom.baitap3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.HistoryCurrency;
import Utils.MyDatabaseHelper;

public class history_activity extends AppCompatActivity {
    private ListView listHistory;
    private ArrayList<HistoryCurrency> historyCurrencyArrayList ;
    private ArrayAdapter<HistoryCurrency> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        addControl();

    }

    private void addControl() {
        listHistory = findViewById(R.id.listHistory);
        historyCurrencyArrayList = new ArrayList<HistoryCurrency>();
        historyCurrencyArrayList.clear();
        historyCurrencyArrayList = new MyDatabaseHelper(this).getAllHistory();
        adapter = new ArrayAdapter<HistoryCurrency>(history_activity.this, android.R.layout.simple_list_item_1,historyCurrencyArrayList);
        listHistory.setAdapter(adapter);
    }
}