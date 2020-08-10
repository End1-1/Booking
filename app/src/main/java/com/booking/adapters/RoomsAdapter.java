package com.booking.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.booking.R;
import com.booking.activities.App;
import com.booking.activities.RoomEditActivity;
import com.booking.gson.GRoom;

public class RoomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;

    public RoomsAdapter(Activity i) {
        mActivity = i;
    }

    private class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private TextView tvPax;
        private TextView tvPrice;

        public VH(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvName = itemView.findViewById(R.id.name);
            tvPax = itemView.findViewById(R.id.pax);
            tvPrice = itemView.findViewById(R.id.price);
        }

        public void bind(int i) {
            GRoom r = GRoom.mList.get(i);
            tvName.setText(r.name);
            tvPax.setText(String.format("%s: %s", App.context().getString(R.string.pax), r.pax));
            tvPrice.setText(String.format("%s: %s", App.context().getString(R.string.price), r.price));
        }

        @Override
        public void onClick(View view) {
            int i = getLayoutPosition();
            GRoom r = GRoom.mList.get(i);
            Intent intent = new Intent(App.context(), RoomEditActivity.class);
            intent.putExtra("room", r.id);
            intent.putExtra("name", r.name);
            intent.putExtra("pax", r.pax);
            intent.putExtra("price", r.price);
            mActivity.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(mActivity.getLayoutInflater().inflate(R.layout.item_rooms, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH h = (VH) holder;
        h.bind(position);
    }

    @Override
    public int getItemCount() {
        return GRoom.mList.size();
    }
}