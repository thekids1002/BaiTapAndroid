package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baitapnhom.baitap3.R;
import java.util.List;
import Model.HistoryCurrency;

public class HistoryAdapter extends ArrayAdapter<HistoryCurrency> {


    @NonNull
    Context context;
    int resource;
    @NonNull
    List<HistoryCurrency> objects;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<HistoryCurrency> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listview, parent, false);
        }

        TextView stt = convertView.findViewById(R.id.ls_stt);
        TextView spn_from = convertView.findViewById(R.id.ls_spn_from);
        TextView spn_to = convertView.findViewById(R.id.ls_spn_to);
        TextView value_from = convertView.findViewById(R.id.ls_value_from);
        TextView value_to = convertView.findViewById(R.id.ls_value_to);
        HistoryCurrency his = objects.get(position);
        stt.setText(String.valueOf(position + 1));
        spn_from.setText(his.getSpn_from());
        spn_to.setText(his.getSpn_to());
        value_from.setText(his.getValue_from());
        value_to.setText(his.getValue_to());

        return convertView;
    }
}
