package com.example.roman.weatherlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Roman on 15.02.2016.
 */
public class CardsRecyclerAdapter extends RecyclerView.Adapter<CardsRecyclerAdapter.ViewHolder> {

    private Context context;
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mCard;
        public ViewHolder(LinearLayout v) {
            super(v);
            mCard = v;
        }
    }

    public CardsRecyclerAdapter(Context context, String[] dataSet) {
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
        TextView textView = (TextView) holder.mCard.findViewById(R.id.city_field);
       /* Drawable img = context.getResources().getDrawable(images[rand.nextInt(7)]);
        ((ImageView)holder.mCard.findViewById(R.id.imageView)).setImageDrawable(img);*/
        textView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
