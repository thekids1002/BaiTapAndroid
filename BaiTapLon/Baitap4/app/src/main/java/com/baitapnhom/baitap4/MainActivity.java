package com.baitapnhom.baitap4;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Adapter.FileModelAdapter;
import Model.FileModel;
import Utils.ReminderBroadCast;

public class MainActivity extends AppCompatActivity {
    private ListView lvFile;
    private TextView textView;
    private ArrayList<FileModel> fileModel = new ArrayList<>();
    private FileModelAdapter fileModelAdapter;
    private File[] files;
    private String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int CONFIRM_CODE = 103;
    public static String dirPath = Environment.getExternalStorageDirectory().toString() +
            "/Android/data/com.example.baitap4/files/Pictures/";
    public static String deleted_path = Environment.getExternalStorageDirectory().toString() +
            "/Android/data/com.example.baitap4/files/Deleted";
    private final long TIME_PUSH_NOTIFY = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermisson();
        addControl();
        getFileInDir();
        addEvent();

    }

    // component
    private void addControl() {
        textView = findViewById(R.id.noImage);
        lvFile = findViewById(R.id.lvfile);
        lvFile.setSmoothScrollbarEnabled(true);
        fileModelAdapter = new FileModelAdapter(MainActivity.this, R.layout.listview_custom, fileModel);
        lvFile.setAdapter(fileModelAdapter);
        registerForContextMenu(lvFile);
    }


    // event
    private void addEvent() {
        lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showImage(i);
            }
        });
    }

    // context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.menu_ct_view:
                showImage(index);
                return true;
            case R.id.menu_ct_delete:
                if (moveFile(fileModel.get(index).getFilepath())) {
                    Toast.makeText(MainActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();

                }
                getFileInDir();
                fileModelAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    String pathFileDelete;

    public boolean moveFile(String currentPath) {
        try {
            String deleted_path = Environment.getExternalStorageDirectory().toString() +
                    "/Android/data/com.example.baitap4/files/Deleted";
            File directory = new File(deleted_path);
            File f = new File(currentPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (f.exists()) {
                f.renameTo(new File(deleted_path + "/" + f.getName()));
                pathFileDelete = deleted_path + "/" + f.getName();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // file

    public boolean deleteFile(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                return f.delete();
            } else {
                try {
                    f.mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    public void getFileInDir() { // đọc các file trong thư mục rồi đưa lên danh sách
        Log.e("TAG", getApplicationInfo().dataDir);
        files = new File[]{};
        File directory = new File(dirPath);
        if (directory.exists()) {
            files = directory.listFiles();
            fileModel.clear();
            textView.setVisibility(View.VISIBLE);
            if (files.length > 0) {
                textView.setVisibility(View.GONE);
                for (int i = 0; i < files.length; i++) {

                    try {
                        if (files[i].length() > 0) {
                            fileModel.add(new FileModel(files[i].getName(), files[i].getAbsolutePath()));
                        } else {
                            deleteFile(files[i].getAbsolutePath());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        if (files != null) {
            fileModelAdapter.notifyDataSetChanged();
        }
    }



    private void showImage(int i) {
        Intent intent = new Intent(MainActivity.this, ImageDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("filename", fileModel.get(i).getFilename());
        bundle.putString("filepath", fileModel.get(i).getFilepath());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Option menu
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
            case R.id.delete:
                openDeletedView();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDeletedView() {
        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        startActivityForResult(intent,104);
    }

    // ask permisson
    private void checkPermisson() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            int readPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int cameraPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA);
            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED ||
                    cameraPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }, 1);
            }
        }
    }

    // take photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Intent intent = new Intent(MainActivity.this, ConfirmImageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("filepath", currentPhotoPath);
            intent.putExtras(bundle);
            startActivityForResult(intent, CONFIRM_CODE);
        }
        if (requestCode == CONFIRM_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                final String result = data.getStringExtra("DATA");
                if (result.equals("save")) {
                    getFileInDir();
                    pushNotification();
                } else if (result.equals("delete")) {
                    if (deleteFile(currentPhotoPath)) {
                        Toast.makeText(getApplicationContext(), "File Deleted", Toast.LENGTH_SHORT).show();
                        dispatchTakePictureIntent();
                    }
                } else {

                }
            }
        }
        if(resultCode == Activity.RESULT_CANCELED){
            getFileInDir();
            fileModelAdapter.notifyDataSetChanged();
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.baitapnhom.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
            // fix xiaomi device
            if (photoFile.length() == 0) {
                deleteFile(currentPhotoPath);
            }
        }
    }

    // notification
    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NOTIFY_APP_SELFI_CHANNEL";
            String des = "MY_CHANNEL";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(ReminderBroadCast.CHANNEL_ID, name, importance);
            notificationChannel.setDescription(des);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void pushNotification() {
        createNotificationChanel();
        Intent intent = new Intent(MainActivity.this, ReminderBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),
                TIME_PUSH_NOTIFY * 1000, pendingIntent);
    }

}