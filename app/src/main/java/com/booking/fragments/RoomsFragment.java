package com.booking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.booking.R;
import com.booking.databinding.FragmentRoomsBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GRoom;
import com.booking.httpqueries.HttpRooms;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class RoomsFragment extends ParentFragment {

    private FragmentRoomsBinding mBind;
    private ArrayList<GRoom> mRooms = new ArrayList<>();
    private RoomsAdapter mAdapter = new RoomsAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBind = FragmentRoomsBinding.inflate(inflater, container, false);
        mBind.rvData.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvData.setAdapter(mAdapter);
        return mBind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpRooms httpRooms = new HttpRooms(this);
        httpRooms.go();
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        if (webResponse > 299) {
            return;
        }
        GAnswer ga = GAnswer.parse(s);
        if (ga == null) {
            return;
        }
        if (ga.ok == 0) {
            return;
        }
        mRooms.clear();
        JsonArray ja = ga.data.getAsJsonArray("rooms");
        for (int i = 0; i < ja.size(); i++) {
            GRoom r = GRoom.parse(ja.get(i).getAsJsonObject());
            mRooms.add(r);
        }
        mAdapter.notifyDataSetChanged();
    }

    private class RoomsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private class VH extends RecyclerView.ViewHolder {
            private TextView tvName;
            private TextView tvPax;
            private TextView tvPrice;

            public VH(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.name);
                tvPax = itemView.findViewById(R.id.pax);
                tvPrice = itemView.findViewById(R.id.price);
            }

            public void bind(int i) {
                GRoom r = mRooms.get(i);
                tvName.setText(r.name);
                tvPax.setText(String.format("%s: %s", getString(R.string.pax), r.pax));
                tvPrice.setText(String.format("%s: %s", getString(R.string.price), r.price));
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.item_rooms, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VH h = (VH) holder;
            h.bind(position);
        }

        @Override
        public int getItemCount() {
            return mRooms.size();
        }
    }
}