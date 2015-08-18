package com.sabareesh.dronedna.DisplayUtils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.sabareesh.dronedna.hardware.SignalModelHandle;

import java.util.Stack;

/**
 * Created by sabareesh on 8/14/15.
 */
public class Disp {
    private static Disp disp;
    final Context context;
    Handler handler;
    private Disp(Context context) {
        this.context = context;
        handler= new Handler();
    }

    public static Disp getInstance(){
        return disp;
    }
    public static void setInstance(Context context){
        disp=new Disp(context);
    }

    public void toast(final String message) {


        final Runnable r = new Runnable() {
            public void run() {


                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            }
        };
        handler.post(r);


    }


}
