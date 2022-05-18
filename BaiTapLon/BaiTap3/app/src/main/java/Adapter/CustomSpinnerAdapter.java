package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baitapnhom.baitap3.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Currency;
import Utils.MyConfig;

public class CustomSpinnerAdapter extends ArrayAdapter<Currency> {
    @NonNull Context context;
    int resource;
    @NonNull List<Currency> objects;

    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Currency> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public int getCount() {
        return objects != null ? objects.size() : 0;
    }

    @Override
    public Currency getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_items,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.spn_image_custom);
        TextView textView = convertView.findViewById(R.id.spn_text_custom);
        textView.setText(objects.get(position).getCountryName());
        String url = MyConfig.getImageLink(objects.get(position).getCountryCode());
        Picasso.get().load(url).centerCrop().resize(75,50).into(imageView);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner_items,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.spn_image_custom);
        TextView textView = convertView.findViewById(R.id.spn_text_custom);
        textView.setText(objects.get(position).getCountryName());
        String url = MyConfig.getImageLink(objects.get(position).getCountryCode());
        Picasso.get().load(url).centerCrop().resize(75,50).into(imageView);
        return convertView;
    }
}
