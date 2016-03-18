package com.example.roman.weatherlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.roman.weatherlist.models.WeatherModel;
import com.example.roman.weatherlist.presenters.WeatherPresenter;

import java.util.ArrayList;

public class CardsRecyclerAdapter extends RecyclerView.Adapter<CardsRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList <WeatherModel> mDataSet = new ArrayList<>();
    private ImageLoader mImageLoader;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mCard;
        public ViewHolder(LinearLayout v) {
            super(v);
            mCard = v;
        }
    }

    public CardsRecyclerAdapter(Context context) {
        this.context = context;
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
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
        WeatherPresenter weatherPresenter = new WeatherPresenter(mDataSet.get(position));

        networkImageView.setImageUrl(weatherPresenter.getIconUrl(), mImageLoader);
        tvCity.setText(weatherPresenter.getCity() + ", " + weatherPresenter.getCountry());
        tvDetails.setText(weatherPresenter.getDescription()
                + "\n" + "Humidity: "
                + weatherPresenter.getHumidity()
                + "\n" + "Pressure: "
                + weatherPresenter.getPressure());
        tvTemperat.setText(weatherPresenter.getTempC());
        tvUpdated.setText("Last update: " + weatherPresenter.getUpdateTime());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(ArrayList<WeatherModel> dataSet){
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }
}
