package com.baitapnhom.baitap3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.HistoryAdapter;
import Model.HistoryCurrency;
import Utils.MyDatabaseHelper;

public class History_Activity extends AppCompatActivity {
     ListView listHistory;
     ArrayList<HistoryCurrency> historyCurrencyArrayList ;
     HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        addControl();
    }
    private void addControl() {
        listHistory = findViewById(R.id.listHistory);
        historyCurrencyArrayList = new ArrayList<>();
        historyCurrencyArrayList.clear();
        historyCurrencyArrayList = new MyDatabaseHelper(this).getAllHistory();
        adapter = new HistoryAdapter(History_Activity.this, R.layout.custom_listview,historyCurrencyArrayList);
        listHistory.setAdapter(adapter);
    }
}