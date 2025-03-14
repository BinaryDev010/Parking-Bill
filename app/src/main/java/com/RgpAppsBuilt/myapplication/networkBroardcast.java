package com.RgpAppsBuilt.myapplication;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.RgpAppsBuilt.myapplication.databinding.NointernetBinding;
public class networkBroardcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (!isNetworkConnected(context)){
            NointernetBinding binding =  NointernetBinding.inflate(LayoutInflater.from(context));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setView(binding.getRoot());
             Dialog dialog = builder.create();
            dialog.show();

            binding.trybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isNetworkConnected(context))
                    {
                        dialog.dismiss();
                    }
                }
            });

        }


    }
    private boolean isNetworkConnected(Context context){
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo !=null && networkInfo.isConnected();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
