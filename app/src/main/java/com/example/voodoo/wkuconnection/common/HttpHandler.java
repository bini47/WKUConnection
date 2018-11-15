package com.example.voodoo.wkuconnection.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by voodoo on 11/10/2017.
 */

public class HttpHandler {

    static String steam=null;

    public HttpHandler(){

    }

    public String GetHttpData(String urlString){
        try{
            URL url= new URL(urlString);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb= new StringBuilder();
                String line;
                while((line = r.readLine())!=null){
                    sb.append(line);
                    steam=sb.toString();
                    urlConnection.disconnect();


                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return steam;


    }

}
