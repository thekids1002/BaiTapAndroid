package com.baitapandroid2.bai3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
//Viết chương trình hiển thị 1 EditText cho ngưòi dùng nhập dữ liệu. Khi ngưòi dùng nhập xong
//và nhấn nút giữa của điện thoại, sẽ hiển thị AlertDialog thông báo chuỗi văn bản vừa nhập.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnShowTime = findViewById(R.id.btnShowTime);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        // xử lý cho button
        btnShowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date t = new Date();
                String message =  "Thời gian hiện hành " + t.toLocaleString();
                alertDialog.setMessage(message);
                alertDialog.show();
            }
        });
    }


}