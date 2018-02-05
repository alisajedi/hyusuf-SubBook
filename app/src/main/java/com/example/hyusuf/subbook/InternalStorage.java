package com.example.hyusuf.subbook;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by hyusuf on 2018-02-01.
 *
 * This class deals with all subscription storage
 *
 *
 */

public class InternalStorage {

    private Context context;
    private static String file;

    /**
     * This function makes an instance of internalStorage given context and filename
     * @param con
     * @param fileName
     */

    public InternalStorage(Context con,String fileName) {
        this.context=con;
        this.file=fileName;
    }

    /**
     * This function returns an ArrayList of subscription objects saved from file
     * @return ArrayList<Subscriptions>
     */
    public ArrayList<Subscriptions> load_Subs() {
        //Subscriptions sub = null;
        File sub_File = new File(context.getFilesDir(), "" + File.separator + file);
        ArrayList<Subscriptions> subList = new ArrayList<Subscriptions>();
        try {
            FileInputStream fis = new FileInputStream(sub_File);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscriptions>>() {
            }.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subList = new ArrayList<Subscriptions>();

        }
        return subList;
    }

    /**
     * This function saves an arrayList of subscription objects on to file
     * @param subList
     */
    public void save_Sub(ArrayList<Subscriptions> subList) {
        File sub_file=new File(context.getFilesDir(),""+File.separator+file);
        try {
            FileOutputStream fos = new FileOutputStream(sub_file);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(subList, out);
            Log.d(TAG, "save_Sub: "+subList);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * This function is responsible for editing a subscription saved on file. Loads the data from file and and finds the position
     * of the old subscription info and updates it with new info
     * @param editSub
     * @param sub
     */
    public void edit_Sub(Subscriptions editSub,Subscriptions sub){
        ArrayList<Subscriptions> subArray=new ArrayList<Subscriptions>();
        Subscriptions sub1;
        subArray=load_Subs();
        for(int i=0;i<subArray.size();i++){
            if(equalTo(sub1=subArray.get(i),sub)){
                subArray.set(i,editSub);
            }
        }
        save_Sub(subArray);
    }

    /**
     * This function given a subscription object deletes it from the file. It loads the array from file
     * and removes the subscription from it.
     * @param sub
     */
    public void delete_Sub(Subscriptions sub){
        ArrayList<Subscriptions> subscriptionsArrayList=new ArrayList<Subscriptions>();
        subscriptionsArrayList=load_Subs();
        Subscriptions temp;
        boolean a;
        for(int i=0;i<subscriptionsArrayList.size();i++){
            temp=subscriptionsArrayList.get(i);
            Log.d(TAG, "delete_Sub: "+temp.getSubName()+sub.getSubName());
            if(a=equalTo(temp,sub)){

                subscriptionsArrayList.remove(temp);
            }
            Log.d(TAG, "delete_Sub: "+a);
        }
        save_Sub(subscriptionsArrayList);
    }

    /**
     * This function given two subscription objects returns true if their content are the same and false otherwise
     * @param sub1
     * @param sub2
     * @return Boolean
     */

    public boolean equalTo(Subscriptions sub1,Subscriptions sub2){
        return (sub1.getSubName().equals(sub2.getSubName())&& sub1.getSubDate().equals(sub2.getSubDate())&& sub1.getSubCharge().equals(sub2.getSubCharge())
                && sub1.getSubComment().equals(sub2.getSubComment()));
    }

}
