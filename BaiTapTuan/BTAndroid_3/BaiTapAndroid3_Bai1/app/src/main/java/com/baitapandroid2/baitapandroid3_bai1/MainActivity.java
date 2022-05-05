package com.baitapandroid2.baitapandroid3_bai1;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.img);
        registerForContextMenu(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ot_Edit:
                Toast.makeText(getApplicationContext(), "Menu Edit", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_ot_title:
                Toast.makeText(getApplicationContext(), "Menu Title", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_ot_tool:
                Toast.makeText(getApplicationContext(), "Menu Tool", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_ot_view:
                Toast.makeText(getApplicationContext(), "Menu View", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu,menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}