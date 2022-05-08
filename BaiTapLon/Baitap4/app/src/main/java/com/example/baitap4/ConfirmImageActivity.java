package com.example.baitap4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ConfirmImageActivity extends AppCompatActivity {
    ImageView imageView ;
    ImageButton btnSave, btnDelete;
    public static final String EXTRA_DATA = "EXTRA_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_image);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SaveImage();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteImage();
            }
        });
    }

    private void DeleteImage() {
        final Intent data = new Intent();
        data.putExtra(EXTRA_DATA, "delete");
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void SaveImage() {
        final Intent data = new Intent();
        data.putExtra(EXTRA_DATA, "save");
        setResult(Activity.RESULT_OK, data);
        finish();
    }



    private void addControl() {
        imageView = findViewById(R.id.image_confirm);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        Bundle bundle = getIntent().getExtras();
        String filepath = (String) bundle.get("filepath");
       // imageView.setImageBitmap(BitmapFactory.decodeFile(filepath));
        Picasso.get().load(new File(filepath)).into(imageView);
    }


    @Override
    public void onBackPressed() {
        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        // Khi này sẽ không trả về data.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}