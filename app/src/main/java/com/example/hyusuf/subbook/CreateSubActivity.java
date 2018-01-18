package com.example.hyusuf.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import static android.content.ContentValues.TAG;



public class CreateSubActivity extends Activity {
    private EditText editName;
    private EditText editDate;
    private EditText editCharge;
    private EditText editComment;
    private static String file="sub.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sublayout);
        editName= (EditText) findViewById(R.id.editTextName);
        editDate=(EditText)findViewById(R.id.editTextDate);
        editCharge=(EditText)findViewById(R.id.editTextCharge);
        editComment=(EditText)findViewById(R.id.editTextComment);

    }
    public void onSubmitClick(View view){
        Subscriptions subscription=new Subscriptions(editName.getText().toString(),editDate.getText().toString(),
                editCharge.getText().toString(),editComment.getText().toString());
        save_Sub(subscription);
        finish();
    }

    private void save_Sub(Subscriptions sub) {
        try{
            FileOutputStream fileOutputStream=openFileOutput(file,MODE_APPEND);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(sub);
            objectOutputStream.flush();
            objectOutputStream.close();
            Toast.makeText(getApplicationContext(), "Text saved",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
