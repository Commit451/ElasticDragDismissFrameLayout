package com.commit451.elasticdragdismisslayout.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * The view holder related to each Cheese item
 * Created by John on 11/24/15.
 */
public class CheeseViewHolder extends RecyclerView.ViewHolder {

    public static CheeseViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cheese, parent, false);
        return new CheeseViewHolder(view);
    }

    public ImageView image;
    public TextView title;

    public CheeseViewHolder(View view) {
        super(view);
        image = view.findViewById(R.id.image);
        title = view.findViewById(R.id.name);
    }

    public void bind(Cheese cheese) {
        image.setImageResource(cheese.getDrawable());
        title.setText(cheese.getName());
    }
}
