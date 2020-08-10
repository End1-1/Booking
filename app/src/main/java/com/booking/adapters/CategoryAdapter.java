package com.booking.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.booking.R;
import com.booking.gson.GRoomCategory;


public class CategoryAdapter extends ArrayAdapter<GRoomCategory> {

    public CategoryAdapter(@NonNull Context context) {
        super(context, R.layout.item_category_row, R.id.category, GRoomCategory.mList);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GRoomCategory item = getItem(position);
        if(convertView == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.item_category_row, parent, false);
        }
        TextView name = convertView.findViewById(R.id.category);
        TextView pax = convertView.findViewById(R.id.pax);
        name.setText(item.name);
        pax.setText(item.pax);
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GRoomCategory item = getItem(position);
        if(convertView == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.item_category_row, parent, false);
        }
        TextView name = convertView.findViewById(R.id.category);
        TextView pax = convertView.findViewById(R.id.pax);
        name.setText(item.name);
        pax.setText(item.pax);
        return convertView;
    }
}
