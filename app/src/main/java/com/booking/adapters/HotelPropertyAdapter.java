package com.booking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.booking.databinding.ItemHotelPropertyBinding;
import com.booking.gson.GRoomOptions;

import java.util.ArrayList;
import java.util.List;

public class HotelPropertyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<GRoomOptions> mProperties = new ArrayList<>();
    public boolean mMultiSelection = true;

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemHotelPropertyBinding bind;

        public VH(ItemHotelPropertyBinding b) {
            super(b.getRoot());
            b.getRoot().setOnClickListener(this);
            b.check.setOnClickListener(this);
            bind = b;
        }

        public void onBind(int position) {
            GRoomOptions p = mProperties.get(position);
            bind.check.setText(p.fname);
            bind.check.setChecked(p.fcheck == 1);
        }

        @Override
        public void onClick(View view) {
            if (!mMultiSelection) {
                for (GRoomOptions r: mProperties) {
                    r.fcheck = 0;
                }
            }
            int position = getAdapterPosition();
            mProperties.get(position).fcheck = bind.check.isChecked() ? 1 : 0;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHotelPropertyBinding b = ItemHotelPropertyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VH) holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        return mProperties.size();
    }

    public String checkedList(String group) {
        String options = "";
        for (GRoomOptions ro: GRoomOptions.mList) {
            if (!group.isEmpty()) {
                if (!group.equals(ro.fparamtype)) {
                    continue;
                }
            }
            if (ro.fcheck > 0) {
                if (options.length() > 0) {
                    options += ",";
                }
                options += ro.fid;
            }
        }
        return options;
    }
}
