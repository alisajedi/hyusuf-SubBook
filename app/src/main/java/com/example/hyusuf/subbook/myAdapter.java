package com.example.hyusuf.subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by hyusuf on 2018-01-18.
 */



public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private ArrayList<Subscriptions> subscriptionsList;
    private Context context;
    private Subscriptions subscription;

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
    // Creates the a View holder objects for the view to be reused when scrolling
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return  new ViewHolder(view);
    }
    //binds the data to the view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       final Subscriptions subscriptions=subscriptionsList.get(position);
       holder.textName.setText(subscriptions.getSubName());
       holder.textDate.setText(subscriptions.getSubDate());
       holder.textCharge.setText(subscriptions.getSubCharge());
       //
       holder.textOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.textOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.edit_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnu_item_edit:
                                //handle menu1 click
                                subscription= subscriptionsList.get(position);
                                Intent myIntent = new Intent(context, CreateSubActivity.class);
                                myIntent.putExtra("subscription",subscription ); //Optional parameters
                                context.startActivity(context.RESULT_OK,myIntent);
                                Toast.makeText(context,"edit item Clicked",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.mnu_item_delete:
                                //handle menu2 click
                                subscription= subscriptionsList.get(position);
                                subscriptionsList.remove(position);

                                Log.d("On delete", "onMenuItemClick: "+subscription.toString());
                                SubDeletion subDeletion=new SubDeletion(subscription,context);
                                subDeletion.deleteSub();
                                notifyDataSetChanged();
                                Toast.makeText(context,"delete item clicked",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }
    //returns the number of items in the subscription list
    @Override
    public int getItemCount() {
        return subscriptionsList.size();
    }

    //describes an item view and data about its place in the recyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textDate;
        public TextView textCharge;
        public TextView textOption;

        public ViewHolder(View itemView) {
            super(itemView);
            textName=(TextView)itemView.findViewById(R.id.textName);
            textDate=(TextView)itemView.findViewById(R.id.textDate);
            textCharge=(TextView)itemView.findViewById(R.id.textCharge);
            textOption=(TextView)itemView.findViewById(R.id.textOption);
        }
    }
}
