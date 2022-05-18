package com.example.modernartui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    TextView redbox1, redbox2, redbox3, yellowbox1, yellowbox2, yellowbox3, bluebox2, bluebox3;
    SeekBar sb = null;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xinquyen();
        addControl();
        addEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_ot_title:
                MyDiaLog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void MyDiaLog() {
        builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Nội dung").setTitle("Tiêu đề");

        builder.setMessage("Có muốn mở trang không")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.vn"));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Xin chao");
        alert.show();
    }


    private void addEvent() {
        sb.setMax(100);
        changeColorEvent();
    }

    private void changeColorEvent() {
        final int[] progChange = {0};
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int[] redArray = {255, 0, 0};
                int[] blueArray = {0, 0, 255};
                int[] yellowArray = {255, 255, 0};

                progChange[0] = i;

                redArray[0] = redArray[0] - (255 / 100) * progChange[0];
                redArray[1] = redArray[1] + (229 / 100) * progChange[0];
                redArray[2] = redArray[2] + (238 / 100) * progChange[0];
                blueArray[0] = blueArray[0] + (255 / 100) * progChange[0];
                blueArray[1] = blueArray[1] + (102 / 100) * progChange[0];
                blueArray[2] = blueArray[2] - (255 / 100) * progChange[0];
                yellowArray[0] = yellowArray[0] - (125 / 100) * progChange[0];
                yellowArray[1] = yellowArray[1] - (255 / 100) * progChange[0];
                yellowArray[2] = yellowArray[2] + (130 / 100) * progChange[0];

                redbox1.setBackgroundColor(Color.rgb(redArray[0], redArray[1], redArray[2]));
                redbox2.setBackgroundColor(Color.rgb(redArray[0], redArray[1], redArray[2]));
                redbox3.setBackgroundColor(Color.rgb(redArray[0], redArray[1], redArray[2]));
                bluebox2.setBackgroundColor(Color.rgb(blueArray[0], blueArray[1], blueArray[2]));
                bluebox3.setBackgroundColor(Color.rgb(blueArray[0], blueArray[1], blueArray[2]));
                yellowbox1.setBackgroundColor(Color.rgb(yellowArray[0], yellowArray[1], yellowArray[2]));
                yellowbox2.setBackgroundColor(Color.rgb(yellowArray[0], yellowArray[1], yellowArray[2]));
                yellowbox3.setBackgroundColor(Color.rgb(yellowArray[0], yellowArray[1], yellowArray[2]));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void xinquyen() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET
            }, 1);
        } else {

        }
    }

    private void addControl() {
        redbox1 = findViewById(R.id.redbox1);
        yellowbox1 = findViewById(R.id.yellowbox1);
        redbox2 = findViewById(R.id.redbox2);
        yellowbox2 = findViewById(R.id.yellowbox2);
        bluebox2 = findViewById(R.id.bluebox2);
        redbox3 = findViewById(R.id.redbox3);
        yellowbox3 = findViewById(R.id.yellowbox3);
        bluebox3 = findViewById(R.id.bluebox3);
        sb = findViewById(R.id.slider);
    }
}