package com.example.lin.boylove.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lin.boylove.R;
import com.example.lin.boylove.entity.Response.Online;

import java.util.List;

/**
 * Created by ryne on 13/10/2017.
 */

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {
    private Context context;
    private List<Online> list;
    private static final String URL_IMAGE = "http://206.189.71.173";

    private OnlineListener listener;

    public OnlineAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_online, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Online online = list.get(position);
        // Processing for display view here
        String imageUrl = URL_IMAGE + online.getImage().getUrl();
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.jlbt_flag)
                .into(holder.imvUser);
    }

    @Override
    public int getItemCount() {
        if (null == list) return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imvUser;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imvUser = (ImageView) itemView.findViewById(R.id.imv_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick();
        }
    }

    public void setData(List<Online> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(OnlineListener listener) {
        this.listener = listener;
    }

    public interface OnlineListener {
        void onClick();
    }
}