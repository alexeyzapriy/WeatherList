package com.example.roman.weatherlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Roman on 15.02.2016.
 */
public class CardsRecyclerAdapter extends RecyclerView.Adapter<CardsRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList <Weather> mDataset;
    private ImageLoader mImageLoader = MySingleton.getInstance(context).getImageLoader();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mCard;
        public ViewHolder(LinearLayout v) {
            super(v);
            mCard = v;
        }
    }

    public CardsRecyclerAdapter(Context context, ArrayList <Weather> dataSet) {
        this.context = context;
        mDataset = dataSet;

    }

    @Override
    public CardsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardsRecyclerAdapter.ViewHolder holder, int position) {
        TextView tvCity = (TextView) holder.mCard.findViewById(R.id.city_field);
        TextView tvUpdated = (TextView) holder.mCard.findViewById(R.id.updated_field);
        TextView tvDetails = (TextView) holder.mCard.findViewById(R.id.details_field);
        TextView tvTemperat = (TextView) holder.mCard.findViewById(R.id.current_temperature_field);
        NetworkImageView networkImageView = (NetworkImageView) holder.mCard.findViewById(R.id.weather_icon);
        networkImageView.setImageUrl(mDataset.get(position).getIconUrl(), mImageLoader);

        tvCity.setText(mDataset.get(position).getCityName().toUpperCase(Locale.US) + ", " + mDataset.get(position).getCountry());
        tvDetails.setText(mDataset.get(position).getDescription().toUpperCase(Locale.US)
                + "\n" + "Humidity: "
                + mDataset.get(position).getHumidity()
                + "\n" + "Pressure: "
                + mDataset.get(position).getPressure());
        tvTemperat.setText(mDataset.get(position).getTemp(true));
        tvUpdated.setText("Last update: " + mDataset.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
