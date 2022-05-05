package com.baitapandroid3_bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn ;
    TextView textView;
    CheckBox checkColor;
    CheckBox checkBold;
    int count = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.CountClicked);
        checkColor = findViewById(R.id.checkColor);
        checkBold = findViewById(R.id.checkBold);
        textView.setSingleLine();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkColor.isChecked()){
                    textView.setTextColor(Color.BLUE);
                }
                else{
                    textView.setTextColor(Color.WHITE);
                }
                if(checkBold.isChecked()){
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
                else{
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
                textView.setText("You've Clicked " + ++count +" times");
            }
        });

    }
}