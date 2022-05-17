package Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baitapnhom.baitap3.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Currency;
import Utils.MyConfig;

public class CustomSpinnerAdapter extends BaseAdapter{
    Context context;
    List<Currency> currencies;
    public CustomSpinnerAdapter(Context context, List<Currency> currencies) {
        this.context = context;
        this.currencies = currencies;
    }

    @Override
    public int getCount() {
        return currencies != null ? currencies.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_items,viewGroup,false);
        ImageView imageView = rootView.findViewById(R.id.spn_image_custom);
        TextView textView = rootView.findViewById(R.id.spn_text_custom);
        textView.setText(currencies.get(i).getCountryName());
        String url = MyConfig.getImageLink(currencies.get(i).getCountryCode());
        Picasso.get().load(url).centerCrop().resize(75,50).into(imageView);
        return rootView;
    }
}
