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

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
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
        createView();

    }



    private ArrayList<Subscriptions> saved_Subs(ArrayList<Subscriptions> list) {
        Subscriptions sub=null;
        ObjectInputStream ois=null;
        File sub_File= new File(getFilesDir(),""+File.separator+file);
        try{
            FileInputStream fis = new FileInputStream(sub_File);
            while(true){
                try{
                    ois = new ObjectInputStream(fis);
                    sub=(Subscriptions) ois.readObject();
                    list.add(sub);
                }catch(EOFException e){
                    break;
                }
            }
            ois.close();
            Log.d(TAG, "get_savedsubs: "+list);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    private void createView(){

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Subscriptions> subsList= new ArrayList<Subscriptions>();
        subsList= saved_Subs(subsList);
        adapter= new myAdapter(subsList,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
    }

    public void CreateSubClick(View view){
        Intent getScreenIntent= new Intent(this,CreateSubActivity.class);
        startActivity(getScreenIntent);
    }




}
