package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baitap4.MainActivity2;
import com.example.baitap4.R;

import java.util.List;

import Model.FileModel;

public class FileModelAdapter extends ArrayAdapter<FileModel> {
    @NonNull Context context;
    int resource ;
    @NonNull List<FileModel> objects;

    public FileModelAdapter(@NonNull Context context, int resource, @NonNull List<FileModel> objects) {

        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_custom, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.imageview);
        TextView fileName = convertView.findViewById(R.id.textView);

        FileModel fileModel = this.objects.get(position);
        imageView.setImageBitmap(BitmapFactory.decodeFile( fileModel.getFilepath()));
        fileName.setText(fileModel.getFilename());

        return convertView;
    }
}
