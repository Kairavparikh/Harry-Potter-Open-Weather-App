package com.example.openweatherapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Weather> {
    List list;
    Context context;
    int xmlResource;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Weather> objects) {
        super(context, resource, objects);
        xmlResource = resource;
        list = objects;
        this.context = context;
    }
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent); // return a view that displays the data at a specified position.
        // We are getting specific so we mute/delete this.
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //convertView = layoutInflater.inflate(xmlResource,parent,false);
        View adapterLayout = layoutInflater.inflate(xmlResource, null);
        TextView date = adapterLayout.findViewById(R.id.textView);
        TextView describe = adapterLayout.findViewById(R.id.textView4);
        TextView min = adapterLayout.findViewById(R.id.textView5);
        TextView max = adapterLayout.findViewById(R.id.textView6);
        ImageView image = adapterLayout.findViewById(R.id.imageView);
        image.setImageResource(getItem(position).getImage());
        date.setText(getItem(position).getTime());
        describe.setText(getItem(position).getDescription());
        min.setText(getItem(position).getMinTemp()+ "F°");
        max.setText(getItem(position).getMaxTemp() + "F°");

        return adapterLayout;
    }

}

