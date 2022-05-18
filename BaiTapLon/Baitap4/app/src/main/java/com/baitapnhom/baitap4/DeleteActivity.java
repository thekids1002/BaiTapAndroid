package com.baitapnhom.baitap4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import Adapter.FileModelAdapter;
import Model.FileModel;

public class DeleteActivity extends AppCompatActivity {
    private ListView lvFile_delete;
    private TextView textView;
    private ArrayList<FileModel> fileModel = new ArrayList<>();
    private FileModelAdapter fileModelAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        addControl();
        addEvent();
        registerForContextMenu(lvFile_delete);
    }

    private void addEvent() {
        lvFile_delete.setOnItemClickListener((adapterView, view, i, l) -> showImage(i));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_deleted_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        String filePath = fileModel.get(index).getFilepath();
        switch (item.getItemId()) {
            case R.id.menu_ct_view_deleted_activity:
                showImage(index);
                return true;
            case R.id.menu_ct_deleted_activity:

                if(deleteFile(filePath)){
                    Toast.makeText(DeleteActivity.this, "Đã xoá vĩnh viễn", Toast.LENGTH_SHORT).show();
                }
                getFileInDir();
                fileModelAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_ct_restore_activity:
                if(moveFile(filePath)){
                    Toast.makeText(DeleteActivity.this, "Đã khôi phục thành công", Toast.LENGTH_SHORT).show();
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
            File f = new File(path);
            if (f.exists()) {
                return f.delete();
            }
        } catch (Exception e) {
        }
        return false;
    }
    public boolean moveFile(String currentPath) {
        try {
            String deleted_path = Environment.getExternalStorageDirectory().toString() +
                    "/Android/data/com.example.baitap4/files/Pictures";
            File directory = new File(deleted_path);
            File f = new File(currentPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (f.exists()) {
                f.renameTo(new File(deleted_path + "/" + f.getName()));
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private void showImage(int i) {
        Intent intent = new Intent(DeleteActivity.this, ImageDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("filename", fileModel.get(i).getFilename());
        bundle.putString("filepath", fileModel.get(i).getFilepath());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void addControl() {
        textView = findViewById(R.id.noImageInDeleted);
        lvFile_delete = findViewById(R.id.lvfile_deleted);
        fileModelAdapter = new FileModelAdapter(DeleteActivity.this,R.layout.listview_custom,fileModel);
        getFileInDir();
        lvFile_delete.setAdapter(fileModelAdapter);
    }
    public void getFileInDir() { // đọc các file trong thư mục rồi đưa lên danh sách
        File[] files = new File[]{};
        File directory = new File(MainActivity.deleted_path);
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
    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

}