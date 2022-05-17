package com.baitapnhom.baitap4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageDisplayActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_dispay_activity);
        addControl();
        addEvent();
    }


    private void addEvent() {

    }

    private void addControl() {
        imageView = findViewById(R.id.imageview);
        Bundle bundle = getIntent().getExtras();
        String filepath = (String) bundle.get("filepath");
//        imageView.setImageBitmap(BitmapFactory.decodeFile(filepath));
        Picasso.get().load(new File(filepath)).into(imageView);
    }
}