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
import com.booking.databinding.FragmentBookingsBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GBookings;
import com.booking.gson.GRoom;
import com.booking.httpqueries.HttpBookings;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;

public class BookingsFragment extends ParentFragment {

    private FragmentBookingsBinding mBind;
    private ArrayList<GBookings> mBookings = new ArrayList<GBookings>();
    private BookingsAdapter mAdapter = new BookingsAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBind = FragmentBookingsBinding.inflate(inflater, container, false);
        mBind.rvData.setLayoutManager(new LinearLayoutManager(mBind.getRoot().getContext()));
        mBind.rvData.setAdapter(mAdapter);
        return mBind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        mBind.lprogress.setVisibility(View.GONE);
        if (webResponse > 299) {
            return;
        }
        GAnswer ga = GAnswer.parse(s);
        if (ga == null || ga.ok == 0) {
            return;
        }
        mBookings.clear();
        JsonArray ja = ga.data.getAsJsonArray("bookings");
        for (int i = 0; i < ja.size(); i++) {
            GBookings r = GBookings.parse(ja.get(i).getAsJsonObject());
            mBookings.add(r);
        }
        mAdapter.notifyDataSetChanged();
    }

    void load() {
        mBind.lprogress.setVisibility(View.VISIBLE);
        HttpBookings httpBookings = new HttpBookings(this);
        httpBookings.go();
    }

    private class BookingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private class VH extends RecyclerView.ViewHolder {
            private TextView tvName;
            private TextView tvPax;
            private TextView tvPrice;
            private TextView tvNights;
            private TextView tvDatefor;
            private TextView tvClient;

            public VH(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.name);
                tvPax = itemView.findViewById(R.id.pax);
                tvNights = itemView.findViewById(R.id.nights);
                tvPrice = itemView.findViewById(R.id.total);
                tvDatefor = itemView.findViewById(R.id.datefor);
                tvClient = itemView.findViewById(R.id.client);
            }

            public void bind(int i) {
                GBookings r = mBookings.get(i);
                tvName.setText(r.room);
                tvDatefor.setText(String.format("%s - %s", r.datefrom, r.dateto));
                tvPax.setText(String.format("%s: %s", getString(R.string.pax), r.pax));
                tvNights.setText(String.format("%s: %s", getString(R.string.nights), r.nights));
                tvPrice.setText(String.format("%s: %s", getString(R.string.price), r.total));
                tvClient.setText(r.client);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookingsAdapter.VH(getLayoutInflater().inflate(R.layout.item_bookings, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VH h = (BookingsAdapter.VH) holder;
            h.bind(position);
        }

        @Override
        public int getItemCount() {
            return mBookings.size();
        }
    }
}