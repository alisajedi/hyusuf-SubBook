package com.example.hyusuf.subbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * This class deals with the Create a Subscription activity receives subscription information from
 * user and calls InternalStorage class to save the subscription.
 */


public class CreateSubActivity extends MainActivity {
    private EditText editName;
    private EditText editDate;
    private EditText editCharge;
    private EditText editComment;
    private Button buttonSubmit;
    private Button buttonReset;
    private Button buttonCancel;
    private InternalStorage storage;
    private Boolean edit = false;
    private Subscriptions editSub;
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
        final String subDate=editDate.getText().toString();

        //This receives intent of subscription that is being edited came from myadapter.java otherwise catches NullPointerException
        try {
            Intent intent = getIntent();
            editSub = (Subscriptions) intent.getSerializableExtra("subscription");
            Log.d("EDIT", "onCreate: " + editSub.getSubName());
            if (editSub != null) {
                editName.setText(editSub.getSubName());
                editCharge.setText(editSub.getSubCharge());
                editDate.setText(editSub.getSubDate());
                editComment.setText(editSub.getSubComment());
                edit = true;

            }
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // This case is triggered when the submission button is clicked. Saves the subscription into storage and makes edits if needed
                    case R.id.buttonSubmit:
                        try {
                            //Validates the Date to be in the correct format
                            if (!ValidateDate(editDate.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "Invalid Date format it should be in this yyyy-mm-dd format",
                                        Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onClick: " + ValidateDate(editName.getText().toString()));
                                break;
                            //Validates the Charge to be in the correct format
                            } else if (Double.parseDouble(editCharge.getText().toString()) < 0) {
                                Toast.makeText(getApplicationContext(), "Invalid Charge please input a value greater than zero",
                                        Toast.LENGTH_LONG).show();
                                break;

                            }
                            else if(editName.getText().toString().matches(".*[a-z].*") == false || editComment.getText().toString().matches(".*[a-z].*") == false){
                                Toast.makeText(getApplicationContext(), "Invalid Input for Name or Comment Please Enter Text Only for Both Fields",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                            //Validates the name and comment to be within a character limit
                            else if(editName.getText().toString().length()>20 || editComment.getText().toString().length()>30){
                                Toast.makeText(getApplicationContext(), "Too Many Characters for your Name or Comment a Maximum 20 characters for Name and 30 for Comment",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                            else if(editName.getText().toString().length()<0){
                                Toast.makeText(getApplicationContext(), "Error Subscription Name Cannot be blank ",
                                        Toast.LENGTH_LONG).show();
                                break;

                            }
                            else {
                                Subscriptions subscription = new Subscriptions(editName.getText().toString(),
                                        editDate.getText().toString(), editCharge.getText().toString(),
                                        editComment.getText().toString());

                                // this if block is executed if there has been an edit button click on the popup menu
                                if (edit == true) {
                                    storage.edit_Sub(subscription, editSub);
                                    finish();
                                } else {
                                    subList = storage.load_Subs();
                                    subList.add(subscription);
                                    storage.save_Sub(subList);
                                    finish();
                                    break;
                                }
                            }
                        }catch(NumberFormatException e){
                            Toast.makeText(getApplicationContext(), "Invalid Input Charge must be an Integer",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                        //This case is triggered when the cancel button is clicked it returns to the Main Screen
                    case R.id.buttonCancel:
                        finish();
                        break;
                    // This case is triggered when the reset button is clicked. It sets all editTexts to be empty
                    case R.id.buttonReset:
                        editName.setText("");
                        editDate.setText("");
                        editCharge.setText("");
                        editComment.setText("");
                        break;

                }

            }

        };
        buttonCancel.setOnClickListener(listener);
        buttonSubmit.setOnClickListener(listener);
        buttonReset.setOnClickListener(listener);


    }
    // this code came from  https://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
    // used on Feb 2, 2018
    public boolean ValidateDate(String editDate){
        if(editDate == null){
            return false;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            sdf.setLenient(false);
            Date date = sdf.parse(editDate);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }



}
