package com.example.hyusuf.subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;

import static android.content.ContentValues.TAG;



public class CreateSubActivity extends Activity {
    private EditText editName;
    private EditText editDate;
    private EditText editCharge;
    private EditText editComment;
    private Button buttonSubmit;
    private Button buttonReset;
    private Button buttonCancel;
    private InternalStorage storage;
    private Boolean edit = false;
    private Subscriptions subscription;
    private static String file = "sub.txt";
    private ArrayList<Subscriptions> subList=new ArrayList<Subscriptions>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: In");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sublayout);
        editName = (EditText) findViewById(R.id.editTextName);
        editDate = (EditText) findViewById(R.id.editTextDate);
        editCharge = (EditText) findViewById(R.id.editTextCharge);
        editComment = (EditText) findViewById(R.id.editTextComment);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        storage= new InternalStorage(getApplicationContext(),file);


        try {
            Intent intent = getIntent();
            subscription = (Subscriptions) intent.getSerializableExtra("subscription");
            Log.d("EDIT", "onCreate: " + subscription.getSubName());
            if (subscription != null) {
                editName.setText(subscription.getSubName());
                editCharge.setText(subscription.getSubCharge());
                editDate.setText(subscription.getSubDate());
                editComment.setText(subscription.getSubComment());
                edit = true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.buttonSubmit:
                        if (edit == true) {
                            Subscriptions editSub = new Subscriptions(editName.getText().toString(),
                                    editDate.getText().toString(), editCharge.getText().toString(),
                                    editComment.getText().toString());
                            storage.edit_Sub(subscription,editSub);
                            finish();
                        } else {
                            Subscriptions subscription = new Subscriptions(editName.getText().toString(),
                                    editDate.getText().toString(), editCharge.getText().toString(),
                                    editComment.getText().toString());

                            subList=storage.load_Subs();
                            subList.add(subscription);
                            storage.save_Sub(subList);
                            finish();
                            break;
                        }

                    case R.id.buttonCancel:
                        finish();
                        break;

                    case R.id.buttonReset:
                        editName.setText("");
                        editDate.setText("");
                        editCharge.setText("");
                        editComment.setText("");
                        break;

                    default:
                        break;
                }

            }

        };
        buttonCancel.setOnClickListener(listener);
        buttonSubmit.setOnClickListener(listener);
        buttonReset.setOnClickListener(listener);


    }



}
