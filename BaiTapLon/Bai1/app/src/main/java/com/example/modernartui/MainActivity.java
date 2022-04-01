package com.example.modernartui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView redbox1, redbox2, redbox3, yellowbox1, yellowbox2, yellowbox3, bluebox2, bluebox3;
    SeekBar sb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
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

                //Make incremental color value changes

                redArray[0] = redArray[0] - (255 / 100) * progChange[0];
                redArray[1] = redArray[1] + (229 / 100) * progChange[0];
                redArray[2] = redArray[2] + (238 / 100) * progChange[0];
                blueArray[0] = blueArray[0] + (255 / 100) * progChange[0];
                blueArray[1] = blueArray[1] + (102 / 100) * progChange[0];
                blueArray[2] = blueArray[2] - (255 / 100) * progChange[0];
                yellowArray[0] = yellowArray[0] - (125 / 100) * progChange[0];
                yellowArray[1] = yellowArray[1] - (255 / 100) * progChange[0];
                yellowArray[2] = yellowArray[2] + (130 / 100) * progChange[0];

                //Set the boxes to new colors

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