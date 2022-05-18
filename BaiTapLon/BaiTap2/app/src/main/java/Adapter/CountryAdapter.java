package Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baitapnhom.baitap2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Country;
import Util.BitmapManager;

public class CountryAdapter extends BaseAdapter {
    @NonNull
    Context context;
    int resource;
    @NonNull
    //  CustomFilter cs;
    ArrayList<Country> originalArray,temp;


    public CountryAdapter(@NonNull Context context,  ArrayList<Country> originalArray) {
        this.context = context;
        this.originalArray = originalArray;
       // this.temp =  this.originalArray;

    }

    @Override
    public int getCount() {
        return originalArray != null ? originalArray.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return originalArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(originalArray.size() == 0){
            return convertView;
        }
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_custom, parent, false);
            holder = new ViewHolder();
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
            holder.image_country = convertView.findViewById(R.id.image_country);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position < originalArray.size()){
            Country country = this.originalArray.get(position);
            holder.countryName.setText(country.getCountry_name());
            Picasso.get().load(country.getImage()).placeholder(R.drawable.progress_animation).into(holder.image_country);
        }

        return convertView;
    }

//    @Override
//    public Filter getFilter() {
//        if(cs == null){
//            cs = new CustomFilter();
//        }
//        return cs;
//    }
//    class CustomFilter extends Filter{
//
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            ArrayList<Country> filters = new ArrayList<>();
//            FilterResults filterResults = new FilterResults() ;
//            if(charSequence != null && charSequence.length() >0){
//                charSequence = charSequence.toString().toLowerCase();
//                filters.clear();
//                for (int i = 0 ; i < temp.size() ; i++ ){
//                    if(temp.get(i).getCountry_name().toLowerCase().contains(charSequence)){
//                        Country country = temp.get(i);
//                        Log.e("TAGGGGGGGGGGGGGG",country.getCountry_name());
//                        filters.add(country);
//                    }
//
//                }
//                filterResults.count = filters.size();
//                filterResults.values = filters;
//            }
//            else{
//                filterResults.count = temp.size();
//                filterResults.values = temp;
//            }
//
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            originalArray = (ArrayList<Country>) filterResults.values;
//            notifyDataSetChanged();
//        }
//    }

//    @NonNull
//    @Override
//    public Filter getFilter() {
//        return searchFilter;
//    }
//
//    private Filter searchFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            ArrayList<Country> filter = new ArrayList<>();
//            FilterResults filterResults = new FilterResults();
//            if (charSequence == null || charSequence.length() == 0 || charSequence.toString().isEmpty()) {
//                Log.e("ATGGG","empty");
//                for (Country  country: temp) {
//                    System.out.println("Mảng temp " +country.getCountry_name());
//                }
//                filter.addAll(temp);
//            } else {
//                String fillterPatern = charSequence.toString().toLowerCase().trim();
//                System.out.println("Chữ điền vào" +fillterPatern);
//                for (Country country : objects) {
//                    if (country.getCountry_name().toLowerCase().contains(fillterPatern)) {
//                        filter.add(country);
//                        System.out.println("Mảng object " +country.getCountry_name());
//                    }
//                }
//            }
//
//            filterResults.values = filter;
//            filterResults.count = filter.size();
//            return filterResults;
//
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            objects = (ArrayList<Country>) filterResults.values;
//            if (filterResults.count > 0) {
//                notifyDataSetChanged();
//            } else {
//                notifyDataSetInvalidated();
//            }
//        }
//    };

    static class ViewHolder {
        ImageView image_country;
        TextView countryName;
    }
}