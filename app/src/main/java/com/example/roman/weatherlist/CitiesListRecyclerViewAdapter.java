package com.example.roman.weatherlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CitiesListRecyclerViewAdapter extends RecyclerView.Adapter<CitiesListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mDataset;
    private Cities cities;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mCard;

        public ViewHolder(RelativeLayout v) {
            super(v);
            mCard = v;
        }
    }

    public CitiesListRecyclerViewAdapter(Activity a) {
        cities = new Cities(a);
        mDataset = cities.getCities();
    }

    @Override
    public CitiesListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CitiesListRecyclerViewAdapter.ViewHolder holder, final int position) {
        TextView tvCityName = (TextView) holder.mCard.findViewById(R.id.city_name);
        ImageView btnDelCity = (ImageView) holder.mCard.findViewById(R.id.del_icon_city);
        tvCityName.setText(mDataset.get(position));

        btnDelCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                notifyDataSetChanged();
                mDataset.remove(position);
                cities.setCities(mDataset);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
