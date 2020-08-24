package com.booking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.booking.databinding.ItemRoomoptionBinding;
import com.booking.gson.GRoomOptions;

public class RoomOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemRoomoptionBinding bind;
        public VH(ItemRoomoptionBinding b) {
            super(b.getRoot());
            b.getRoot().setOnClickListener(this);
            bind = b;
        }

        public void onBind(int position) {
            GRoomOptions go = GRoomOptions.mList.get(position);
            bind.name.setText(go.fname);
            bind.ch.setChecked(go.fcheck > 0);
            bind.group.setText(go.ftype);
        }

        @Override
        public void onClick(View v) {
            bind.ch.setChecked(!bind.ch.isChecked());
            int position = getAdapterPosition();
            GRoomOptions.mList.get(position).fcheck = bind.ch.isChecked() ? 1 : 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRoomoptionBinding b = ItemRoomoptionBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VH v = (VH) viewHolder;
        v.onBind(i);
    }

    @Override
    public int getItemCount() {
        return GRoomOptions.mList.size();
    }
}
