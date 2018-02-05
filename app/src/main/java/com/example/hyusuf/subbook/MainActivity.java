package com.example.hyusuf.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class is responsible for the Any Activity occurring on the main screen
 */


public class MainActivity extends Activity {
    private static final String TAG="Main Activity";
    private static final String file="sub.txt";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Subscriptions> savedSubs;
    private InternalStorage storage;
    private TextView textTotal;
    private Button newSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTotal=(TextView) findViewById(R.id.textViewCharge1);
        newSub=(Button) findViewById(R.id.buttonCreate);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        createView();


        // On clickListener for Create New Button on Main Screen
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getScreenIntent = new Intent(getApplicationContext(), CreateSubActivity.class);
                startActivity(getScreenIntent);
            }
        };
        newSub.setOnClickListener(listener);




         //this try block deals with letting the main screen know to update the monthly total charge
        // editText because there has been a subscription deletion
        try{
            Bundle extras = getIntent().getExtras();
            String msg = extras.getString("keyMessage");
            if (msg=="update"){
                double total=totalCost(savedSubs);
                textTotal.setText(Double.toString(total));
            }
        }catch(NullPointerException e){

        }

    }

    /**
     * this function returns the total cost of the subscriptions saved on app.
     * @param sub
     * @return int total cost
     */
    private double totalCost(ArrayList<Subscriptions> sub){
       double total=0.00;
        for(int i=0;i<sub.size();i++){
           total=total+Double.parseDouble(sub.get(i).getSubCharge());
       }
       return total;
    }

    /**
     * This function loads the data from file and gives it to adapter the initialize the recycle view Main Screen
     */
    private void createView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        InternalStorage storage= new InternalStorage(getApplicationContext(),file);
        savedSubs=storage.load_Subs();
        double total=totalCost(savedSubs);
        textTotal.setText(Double.toString(total));
        adapter= new myAdapter(savedSubs,this);
        recyclerView.setAdapter(adapter);

    }

    /**
     * This override calls the createView method so that it displays the view after any activity occurs such as new subscription added.
     */
    @Override
    protected void onResume() {
        super.onResume();
        createView();
    }


}
