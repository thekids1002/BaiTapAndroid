package com.example.tuan4bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btnview);
        EditText name = findViewById(R.id.txtName);
        EditText mail = findViewById(R.id.txtEmail);
        EditText project = findViewById(R.id.txtProject);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("project",project.getText().toString());
                intent.putExtra("mail",mail.getText().toString());
                startActivity(intent);
            }
        });
    }
}