package com.stickynotes.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stickynotes.R;

/**
 * Created by TrungTV on 03/08/2017.
 */

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.MyViewHolder> {

    private int[] listImage;
    private int mSelectedItem ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBackground, imgTick;

        public MyViewHolder(View view) {
            super(view);
            imgBackground = (ImageView) view.findViewById(R.id.imgBackground);
            imgTick = (ImageView) view.findViewById(R.id.imgTick);
        }
    }

    public BackgroundAdapter(int[] listImage) {
        this.listImage = listImage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_background, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.imgBackground.setImageResource(listImage[position]);
        holder.imgTick.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedItem = position;
                notifyDataSetChanged();
            }
        });

        if(position==mSelectedItem){
            holder.imgTick.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listImage.length;
    }
}
