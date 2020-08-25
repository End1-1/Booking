package com.booking.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.booking.databinding.ItemBedOptionBinding;
import com.booking.gson.GObject;

import java.util.ArrayList;
import java.util.List;

public class BedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class GBed extends GObject {
        public String name;
        public String id;
        public String qty;
    }

    public List<GBed> mList = new ArrayList<>();

    private class Item extends RecyclerView.ViewHolder {

        private ItemBedOptionBinding bind;

        public Item(ItemBedOptionBinding b) {
            super(b.getRoot());
            bind = b;
        }

        public void onBind(int position) {
            GBed b = mList.get(position);
            bind.name.setText(b.name);
            bind.qty.removeTextChangedListener(w);
            bind.qty.setText(b.qty);
            bind.qty.addTextChangedListener(w);
        }

        TextWatcher w = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GBed b = mList.get(getAdapterPosition());
                b.qty = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBedOptionBinding b = ItemBedOptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Item(b);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Item) holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
