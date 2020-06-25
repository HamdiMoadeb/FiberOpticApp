package com.outsider.networkapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterListData extends ArrayAdapter<Routes> {

    Context ctx;
    ArrayList<Routes> data;
    public AdapterListData( Context context, int resource,  ArrayList<Routes> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_data, parent, false);

        TextView event = convertView.findViewById(R.id.eventtv);
        TextView distance = convertView.findViewById(R.id.distancetv);
        TextView loss = convertView.findViewById(R.id.losstv);

        Routes routes = getItem(position);
        event.setText(routes.getEvent());
        distance.setText(routes.getDistance());
        loss.setText(routes.getLoss());

        return convertView;
    }
}
