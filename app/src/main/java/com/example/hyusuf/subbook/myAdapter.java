package com.example.hyusuf.subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;




/**
 * Created by hyusuf on 2018-01-18.
 *
 * This class is the adapter that helps display the recycleView on the Main Screen
 *
 *
 */



public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private ArrayList<Subscriptions> subscriptionsList;
    private Context context;
    private Subscriptions subscription;
    private static final String file="sub.txt";


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * This function makes and adapter instance given an ArrayList and Context
     * @param subscriptionsList
     * @param context
     */
    public myAdapter(ArrayList<Subscriptions> subscriptionsList, Context context) {
        this.subscriptionsList = subscriptionsList;
        this.context = context;
    }

    /**
     * Creates the a View holder objects for the view to be reused when scrolling
     * @param parent
     * @param viewType
     * @return ViewHolder
     */

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
       holder.textCharge.setText("$"+ subscriptions.getSubCharge());
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
                            // triggered when edit popup button clicked
                            case R.id.mnu_item_edit:
                                subscription= subscriptionsList.get(position);
                                Intent myIntent = new Intent(context, CreateSubActivity.class);
                                myIntent.putExtra("subscription",subscription ); //Optional parameters
                                context.startActivity(myIntent);
                                notifyDataSetChanged();
                                Toast.makeText(context,"edit item Clicked",Toast.LENGTH_LONG).show();
                                break;
                            // triggered when delete popup button clicked
                            case R.id.mnu_item_delete:
                                subscription= subscriptionsList.get(position);
                                subscriptionsList.remove(position);
                                InternalStorage internalStorage=new InternalStorage(context,file);
                                internalStorage.delete_Sub(subscription);
                                notifyDataSetChanged();
                                String msg = "update";
                                Intent i = new Intent(context, MainActivity.class);
                                i.putExtra("keyMessage",msg);
                                context.startActivity(i);
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

    /**
     *  This function returns the number of items in the subscription list
     * @return ArrayList of subscriptions size
     */
    @Override
    public int getItemCount() {
        return subscriptionsList.size();
    }

    /**
     * This class is responsible for setting item view and data on its place in the recyclerView
     *
     */

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
