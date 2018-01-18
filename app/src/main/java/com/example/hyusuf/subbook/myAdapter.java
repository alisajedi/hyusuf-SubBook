package com.example.hyusuf.subbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyusuf on 2018-01-18.
 */



public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private ArrayList<Subscriptions> subscriptionsList;
    private Context context;

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public myAdapter(ArrayList<Subscriptions> subscriptionsList, Context context) {
        this.subscriptionsList = subscriptionsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Subscriptions subscriptions=subscriptionsList.get(position);
       holder.textName.setText(subscriptions.getSubName());
       holder.textDate.setText(subscriptions.getSubDate());
       holder.textCharge.setText(subscriptions.getSubCharge());

    }

    @Override
    public int getItemCount() {
        return subscriptionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textName;
        public TextView textDate;
        public TextView textCharge;

        public ViewHolder(View itemView) {
            super(itemView);
            textName=(TextView)itemView.findViewById(R.id.textName);
            textDate=(TextView)itemView.findViewById(R.id.textDate);
            textCharge=(TextView)itemView.findViewById(R.id.textCharge);
        }
    }
}
