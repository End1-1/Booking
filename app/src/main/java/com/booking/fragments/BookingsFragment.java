package com.booking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.booking.R;
import com.booking.activities.VoucherActivity;
import com.booking.databinding.FragmentBookingsBinding;
import com.booking.databinding.ItemBookingsBinding;
import com.booking.gson.GAnswer;
import com.booking.gson.GBookings;
import com.booking.httpqueries.HttpBookings;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class BookingsFragment extends ParentFragment {

    private boolean mFlagArrives = false;
    private boolean mFlagDepartures = false;
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
        Bundle b = getArguments();
        if (b != null) {
            mFlagArrives = b.getBoolean("arrives", false);
            mFlagDepartures = b.getBoolean("departures", false);
        }
        load();
    }

    @Override
    public void webResponse(int code, int webResponse, String s) {
        mBind.lprogress.setVisibility(View.GONE);
        if (webResponse > 299) {
            return;
        }
        GAnswer ga = GAnswer.parse(s, GAnswer.class);
        if (ga == null || ga.ok == 0) {
            return;
        }
        mBookings.clear();
        JsonArray ja = ga.data.getAsJsonArray("bookings");
        for (int i = 0; i < ja.size(); i++) {
            GBookings r = GBookings.parse(ja.get(i).getAsJsonObject(), GBookings.class);
            mBookings.add(r);
        }
        mAdapter.notifyDataSetChanged();
    }

    void load() {
        mBind.lprogress.setVisibility(View.VISIBLE);
        HttpBookings httpBookings = new HttpBookings(mFlagArrives, mFlagDepartures, this);
        httpBookings.go();
    }

    private class BookingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public int mSelectionPos = RecyclerView.NO_POSITION;

        private class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ItemBookingsBinding mBind;

            public VH(@NonNull ItemBookingsBinding b) {
                super(b.getRoot());
                mBind = b;
                b.getRoot().setOnClickListener(this);
            }

            public void bind(int i) {
                GBookings r = mBookings.get(i);
                mBind.name.setText(r.room);
                mBind.datefor.setText(String.format("%s - %s", r.datefrom, r.dateto));
                mBind.pax.setText(String.format("%s: %s", getString(R.string.pax), r.pax));
                mBind.nights.setText(String.format("%s: %s", getString(R.string.nights), r.nights));
                mBind.total.setText(String.format("%s: %s", getString(R.string.price), r.total));
                mBind.client.setText(r.client);
            }

            @Override
            public void onClick(View view) {
                mSelectionPos = getLayoutPosition();
                Intent intent = new Intent(getContext(), VoucherActivity.class);
                intent.putExtra("id", mBookings.get(mSelectionPos).id);
                startActivity(intent);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemBookingsBinding bookingsBinding = ItemBookingsBinding.inflate(getLayoutInflater(), parent, false);
            return new BookingsAdapter.VH(bookingsBinding);
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