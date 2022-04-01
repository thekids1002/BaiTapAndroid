package com.example.baitap4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.FileModel;
import adapter.FileModelAdapter;
// #################################################################
// #                             _`
// #                          _ooOoo_
// #                         o8888888o
// #                         88" . "88
// #                         (| -_- |)
// #                         O\  =  /O
// #                      ____/`---'\____
// #                    .'  \\|     |//  `.
// #                   /  \\|||  :  |||//  \
// #                  /  _||||| -:- |||||_  \
// #                  |   | \\\  -  /'| |   |
// #                  | \_|  `\`---'//  |_/ |
// #                  \  .-\__ `-. -'__/-.  /
// #                ___`. .'  /--.--\  `. .'___
// #             ."" '<  `.___\_<|>_/___.' _> \"".
// #            | | :  `- \`. ;`. _/; .'/ /  .' ; |
// #            \  \ `-.   \_\_`. _.'_/_/  -' _.' /
// #=============`-.`___`-.__\ \___  /__.-'_.'_.-'=================#
//                            `=--=-'
//           _.-/`)
//          // / / )
//       .=// / / / )
//      //`/ / / / /
//     // /     ` /
//    ||         /
//     \\       /
//      ))    .'
//     //    /
//          /
// Nếu không load ảnh thì sửa file path dòng 92 , không biết lấy đường dẫn tương đối.
public class MainActivity extends AppCompatActivity {
    ListView lvFile;
    ArrayList<FileModel> fileModel = new ArrayList<FileModel>();
    FileModelAdapter fileModelAdapter;
    File[] files;
    String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermisson();
        addControl();
        getFileInDir();
    }

    private void addControl() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bài 4"); //Thiết lập tiêu đề nếu muốn
        //imageView = findViewById(R.id.imageview);
        lvFile = findViewById(R.id.lvfile);
        getFileInDir();
        fileModelAdapter = new FileModelAdapter(MainActivity.this, R.layout.listview_custom, fileModel);
        lvFile.setAdapter(fileModelAdapter);
    }

    public void getFileInDir() { // đọc các file trong thư mục rồi đưa lên danh sách
        files = new File[]{};
        File directory = new File("/storage/emulated/0/Android/data/com.example.baitap4/files/Pictures/");
        files = directory.listFiles();
        fileModel.clear();
        for (int i = 0; i < files.length; i++) {
            fileModel.add(new FileModel(files[i].getName(), files[i].getAbsolutePath()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                dispatchTakePictureIntent();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void checkPermisson() { // kiểm tra xin quyền camera
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, CAMERA_REQUEST_CODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            getFileInDir();
            fileModelAdapter.notifyDataSetChanged();

        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}