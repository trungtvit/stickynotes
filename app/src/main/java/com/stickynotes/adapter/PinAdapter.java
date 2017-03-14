package com.stickynotes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stickynotes.R;

/**
 * Created by TrungTV on 03/08/2017.
 */

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.MyViewHolder> {

    private int[] listPin;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPin;

        public MyViewHolder(View view) {
            super(view);
            imgPin = (ImageView) view.findViewById(R.id.imgPin);
        }
    }

    public PinAdapter(int[] listPin) {
        this.listPin = listPin;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pin, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.imgPin.setImageResource(listPin[position]);
    }

    @Override
    public int getItemCount() {
        return listPin.length;
    }
}
