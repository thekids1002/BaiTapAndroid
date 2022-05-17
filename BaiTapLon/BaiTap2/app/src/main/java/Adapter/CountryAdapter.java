package Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baitapnhom.baitap2.R;
import java.util.List;
import Model.Country;
import Util.BitmapManager;

public class CountryAdapter extends ArrayAdapter<Country> {
    @NonNull
    Context context;
    int resource;
    @NonNull
    List<Country> objects;
    public CountryAdapter(@NonNull Context context, int resource, @NonNull List<Country> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
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
        //holder.areaInSqKm.setText(country.getAreaInSqKm());
        //holder.population.setText(country.getPopulation());
        // Picasso.get().load(country.getImage()).placeholder(R.drawable.progress_animation).into(holder.image_country);
        //  holder.image_country.setImageBitmap(BitmapFactory.decodeStream((InputStream)new URL("http://www.mac-wallpapers.com/bulkupload/wallpapers/Apple%20Wallpapers/apple-black-logo-wallpaper.jpg").getContent()));
//        if(checkbitmap(country.getBitmapImage())){
//            Log.e("TAG Da co bit map", "ok");
//            holder.image_country.setImageBitmap(country.getBitmapImage());
//            return  convertView;
//        }
//        else{
//            new Thread () {
//                boolean success = false;
//                public void run() {
//                    try {
//                        URL url = new URL(country.getImage());
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        connection.setDoInput(true);
//                        connection.connect();
//                        InputStream input = connection.getInputStream();
//                        Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                        holder.image_country.setImageBitmap(myBitmap);
//                        objects.get(position).setBitmapImage(myBitmap);
//                        if(checkbitmap(objects.get(position).getBitmapImage())){
//                            Log.e("Đã lưu bitmap",country.getImage());
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        success = false;
//                    }
////                mHandler.post(new Runnable() {
////                    public void run() {
////
////                        try {
////
////                        }
////                        catch (Exception e){
////                            e.printStackTrace();
////                        }
////                    }
////                });
//                }
//            }.start();
//        }

        BitmapManager.INSTANCE.loadBitmap(country.getImage(), holder.image_country, 100,
                62);
        return convertView;
    }



    static class ViewHolder {
        ImageView image_country;
        TextView countryName;
    }
}