package com.baitapnhom.baitap4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ConfirmImageActivity extends AppCompatActivity {
    ImageView imageView ;
    ImageButton btnSave, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_image);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnSave.setOnClickListener(view -> SaveImage());

        btnDelete.setOnClickListener(view -> DeleteImage());
    }

    private void DeleteImage() {
        final Intent data = new Intent();
        data.putExtra("DATA", "delete");
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void SaveImage() {
        final Intent data = new Intent();
        data.putExtra("DATA", "save");
        setResult(Activity.RESULT_OK, data);
        finish();
    }



    private void addControl() {
        imageView = findViewById(R.id.image_confirm);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        Bundle bundle = getIntent().getExtras();
        String filepath = (String) bundle.get("filepath");
        File f = new File(filepath);
        if(f.length() <= 0){
            onBackPressed();
            finish();
            return;
        }
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