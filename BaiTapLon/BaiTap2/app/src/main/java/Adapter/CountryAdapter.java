package Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baitapnhom.baitap2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import Model.Country;
import Util.BitmapManager;

public class CountryAdapter extends ArrayAdapter<Country> implements Filterable {
    @NonNull
    Context context;
    int resource;
    @NonNull
    ArrayList<Country> objects;
    ArrayList<Country> search;
    public CountryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Country> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.search = new ArrayList<>(objects);
        BitmapManager.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.load));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        Country country = this.objects.get(position);
        holder.countryName.setText(country.getCountry_name());
        Picasso.get().load(country.getImage()).placeholder(R.drawable.progress_animation).into(holder.image_country);
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           ArrayList<Country> filter = new ArrayList<>();
           if(charSequence == null || charSequence.length() == 0){
               filter.addAll(objects);
           }
           else{
               String fillterPatern = charSequence.toString().toLowerCase().trim();
               for (Country country : objects){
                   if(country.getCountry_name().toLowerCase().contains(fillterPatern)){
                       search.add(country);
                   }
               }
           }
           FilterResults filterResults =new FilterResults();
           filterResults.values = filter;
           return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            search.clear();
            search.addAll((Collection<? extends Country>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    static class ViewHolder {
        ImageView image_country;
        TextView countryName;
    }
}