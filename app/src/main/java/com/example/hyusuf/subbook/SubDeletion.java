package com.example.hyusuf.subbook;

import android.content.Context;

import android.util.Log;

import java.io.EOFException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.ContentValues.TAG;


public class SubDeletion {
    private Subscriptions subscription;
    private Subscriptions editsub;
    private static final String tempfile="temp.txt";
    private static final String file="sub.txt";
    private Context context;

    public SubDeletion(Subscriptions sub, Context con) {
        this.subscription=sub;
        this.context=con;
    }
    public SubDeletion(Subscriptions oldsub, Context con, Subscriptions editsub){
        this.subscription=oldsub;
        this.context=con;
        this.editsub=editsub;
    }
    public void deleteSub(boolean edit) {
        File contentFile=new File(context.getApplicationContext().getFilesDir()+"/"+file);
        File tempFile=new File(context.getApplicationContext().getFilesDir()+"/"+tempfile);
        ObjectInputStream ois = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            FileInputStream fis = new FileInputStream(contentFile);
            Subscriptions sub=null;
            while (true) {
                try {
                    ois = new ObjectInputStream(fis);
                    sub = (Subscriptions) ois.readObject();
                    Log.d(TAG, "deleteSub: "+sub.toString());
                    if (this.equalTo(sub)) {
                        if (edit==true){
                            Log.d(TAG, "deleteSub: edit tag");
                            objectOutputStream.writeObject(editsub);
                            objectOutputStream.flush();
                        }
                        else{
                            Log.d(TAG, "deleteSub: continue");
                            continue;

                        }
                    }
                    else{
                        Log.d(TAG, "deleteSub: "+"not equal if block");
                        objectOutputStream.writeObject(sub);
                        objectOutputStream.flush();
                    }
                }catch(EOFException e){
                    break;
                }
            }
            ois.close();
            objectOutputStream.close();
            File file1=context.getFileStreamPath(tempfile);
            if(file1.length()<=7){
                boolean delete=tempFile.delete();
                boolean delete2=contentFile.delete();
            }
            else {
                boolean successful = tempFile.renameTo(contentFile);
                Log.d(TAG, "subDeletion: out " + successful);
            }

            Log.d(TAG, "deleteSub: ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean equalTo(Subscriptions sub) {
        return (sub.getSubName().equals(subscription.getSubName())&& sub.getSubDate().equals(subscription.getSubDate())
                && sub.getSubCharge().equals(subscription.getSubCharge()) && sub.getSubComment().equals(subscription.getSubComment()));
    }












}
