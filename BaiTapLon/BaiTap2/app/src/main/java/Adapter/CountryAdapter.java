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
import com.example.baitap2.R;
import com.squareup.picasso.Picasso;
import java.util.List;

import Model.Country;

public class CountryAdapter extends ArrayAdapter<Country> {

    @NonNull Context context;
    int resource;
    @NonNull List<Country>  objects;

    public CountryAdapter(@NonNull Context context, int resource, @NonNull List<Country>  objects) {

        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_custom, parent, false);
            holder = new ViewHolder();
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
           // holder.population = (TextView) convertView.findViewById(R.id.population);
         //   holder.areaInSqKm = (TextView) convertView.findViewById(R.id.areaInSqKm);
            holder.image_country = convertView.findViewById(R.id.image_country);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageView imageView = convertView.findViewById(R.id.image_country);
        TextView countryName = convertView.findViewById(R.id.countryName);
//        TextView population = convertView.findViewById(R.id.population);
//        TextView areaInSqKm = convertView.findViewById(R.id.areaInSqKm);
        Country country = this.objects.get(position);
        holder.countryName.setText(country.getCountry_name());
        //holder.areaInSqKm.setText(country.getAreaInSqKm());
        //holder.population.setText(country.getPopulation());
        Picasso.get().load(country.getImage()).into(holder.image_country);
        return convertView;
    }
    static class ViewHolder {
        ImageView image_country;
       // TextView areaInSqKm;
        TextView countryName;
      //  TextView population;
    }
}