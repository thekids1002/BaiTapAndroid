package com.example.baitap4;

import android.Manifest;
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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapter.FileModelAdapter;
import Model.FileModel;
import Utils.ReminderBoardCast;
import Utils.ShowNotification;

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
    private ListView lvFile;
    private ArrayList<FileModel> fileModel = new ArrayList<FileModel>();
    private FileModelAdapter fileModelAdapter;
    private File[] files;
    private String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 102;
    private String dirPath = "/storage/emulated/0/Android/data/com.example.baitap4/files/Pictures/";
    private final long TIME_PUSH_NOTIFI = 5; // thoừi gian mặc định 5s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChanel();
        checkPermisson();
        addControl();
        getFileInDir();
        addEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        pushNoti();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pushNoti();
        startService(new Intent(this, ShowNotification.class));
    }

    public void closeApp(View view) {
        finish();
    }

    private void addControl() {
        ActionBar actionBar = getSupportActionBar();
        lvFile = findViewById(R.id.lvfile);
        getFileInDir();
        fileModelAdapter = new FileModelAdapter(MainActivity.this, R.layout.listview_custom, fileModel);
        lvFile.setAdapter(fileModelAdapter);
        registerForContextMenu(lvFile);
    }

    private void addEvent() {
        lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showImage(i);
            }
        });
    }

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
                if(deleteFile(fileModel.get(index).getFilepath())){
                    Toast.makeText(MainActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();
                }
                getFileInDir();
                fileModelAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public boolean deleteFile(String path) {
        try {
            File f = new File(path);//full path like c:/home/ri
            if (f.exists()) {
                return f.delete();
            } else {
                try {
                    //f.createNewFile();//this will create a file
                    f.mkdir();//this create a folder
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    private void showImage(int i) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("filename", fileModel.get(i).getFilename());
        bundle.putString("filepath", fileModel.get(i).getFilepath());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getFileInDir() { // đọc các file trong thư mục rồi đưa lên danh sách
        files = new File[]{};
        File directory = new File(dirPath);
        if(directory.exists()){
            files = directory.listFiles();
            fileModel.clear();
            if(files.length > 0){
                for (int i = 0; i < files.length; i++) {
                    fileModel.add(new FileModel(files[i].getName(), files[i].getAbsolutePath()));
                }
            }
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
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    //
//    private void pushNoti() {
//        Intent notificationIntent = new Intent(getApplicationContext(), ShowNotification.class);
//        PendingIntent contentIntent = PendingIntent.getService(getApplicationContext(), 0, notificationIntent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        am.cancel(contentIntent);
//        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + TIME_PUSH_NOTIFI, contentIntent);
//    }
    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notifyAppSelfyChannel";
            String des = "Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("notifyAppSelfy", name, importance);
            notificationChannel.setDescription(des);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void pushNoti() {
        Intent intent = new Intent(MainActivity.this, ReminderBoardCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (TIME_PUSH_NOTIFI * 1000), pendingIntent);
    }
}