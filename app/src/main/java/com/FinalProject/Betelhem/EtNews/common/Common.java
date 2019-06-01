package com.FinalProject.Betelhem.EtNews.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.FinalProject.Betelhem.EtNews.Retrofit.NewsApi;
import com.FinalProject.Betelhem.EtNews.Retrofit.RetrofitClient;

public class Common {

    public static String currentProductId = null;
    public static String currentUsertoken=null;
    public static NewsApi getApi(){
        return  RetrofitClient.getInstance().create(NewsApi.class);
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info!=null)
            {
                for (int i =0; i<info.length; i++)
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
            }
        }
        return false;
    }

    public static String formatString(String pname) {
        //if character is to long, sub string
        StringBuilder result = new StringBuilder(pname.length() > 70 ? pname.substring(0,70)+ "...": pname);
        return  result.toString();
    }
}
