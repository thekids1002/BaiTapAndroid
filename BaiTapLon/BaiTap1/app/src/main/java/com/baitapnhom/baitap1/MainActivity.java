package com.baitapnhom.baitap1;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView box1, box2, box3, box4, box5, box6, box7, box8, box9;
    SeekBar sb = null;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        builder.setTitle("Inspired by the works of artists such at \n Pie Mondrian and Ben Nicholson.");
        builder.setMessage("Click below to learn more!")
                .setCancelable(false)
                .setPositiveButton("Visit MOMA", (dialog, id) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org"));
                    startActivity(intent);
                })
                .setNegativeButton("Not Now", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void addEvent() {
        sb.setMax(100);
        changeColorEvent();
    }

    int[] redArray = {255, 0, 0};
    int[] blueArray = {0, 0, 255};
    int[] yellowArray = {255, 255, 0};
    int[] greenArray = {0, 100, 0};

    private void changeColorEvent() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                // red
                box1.setBackgroundColor(Color.rgb(redArray[0] - i, redArray[1] + i, redArray[2] + i));
                box8.setBackgroundColor(Color.rgb(redArray[0] - i, redArray[1] + i, redArray[2] + i));
                box3.setBackgroundColor(Color.rgb(redArray[0] - i, redArray[1] + i, redArray[2] + i));


                // yellow
                box2.setBackgroundColor(Color.rgb(yellowArray[0] - i, yellowArray[1] - i, yellowArray[2]));
                box9.setBackgroundColor(Color.rgb(yellowArray[0] - i, yellowArray[1] - i, yellowArray[2]));


                // green
                box4.setBackgroundColor(Color.rgb(greenArray[0] + i, greenArray[1] + i, greenArray[2] + i));
                box6.setBackgroundColor(Color.rgb(greenArray[0] + i, greenArray[1] + i, greenArray[2] + i));

                // blue
                box5.setBackgroundColor(Color.rgb(blueArray[0] + i, blueArray[1] + i, blueArray[2] - i));
                box7.setBackgroundColor(Color.rgb(blueArray[0] + i, blueArray[1] + i, blueArray[2] - i));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void addControl() {
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        box3 = findViewById(R.id.box3);
        box4 = findViewById(R.id.box4);
        box5 = findViewById(R.id.box5);
        box6 = findViewById(R.id.box6);
        box7 = findViewById(R.id.box7);
        box8 = findViewById(R.id.box8);
        box9 = findViewById(R.id.box9);
        sb = findViewById(R.id.slider);
    }
}