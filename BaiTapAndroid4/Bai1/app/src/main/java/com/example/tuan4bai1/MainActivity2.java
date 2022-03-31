package com.example.tuan4bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView txtname, txtmail, txtproject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String mail = intent.getStringExtra("mail");
        String project = intent.getStringExtra("project");
        txtname = findViewById(R.id.txtName);
        txtmail = findViewById(R.id.txtEmail);
        txtproject = findViewById(R.id.txtProject);
        txtname.setText(name);
        txtmail.setText(mail);
        txtproject.setText(project);

    }

    public void manhinh2(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}