package com.example.hyusuf.subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private static final String TAG="Main Activity";
    private static final String file="sub.txt";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Subscriptions> savedSubs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    private void createView(){
        Log.d(TAG, "createView: in CreateView");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Subscriptions> subsList= new ArrayList<Subscriptions>();
        InternalStorage storage= new InternalStorage(getApplicationContext(),file);
        subsList=storage.load_Subs();
        adapter= new myAdapter(subsList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: on resume");
        createView();
    }

    public void CreateSubClick(View view){
        Intent getScreenIntent= new Intent(this,CreateSubActivity.class);
        startActivity(getScreenIntent);
    }




}
