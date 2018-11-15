package com.example.voodoo.wkuconnection;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.voodoo.wkuconnection.Adapter.ArrayAdapterFactory;
import com.example.voodoo.wkuconnection.Adapter.MyAdapter;
import com.example.voodoo.wkuconnection.Model.RssObject;

import com.example.voodoo.wkuconnection.common.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RssObject rssObject;
    SwipeRefreshLayout pullTorefresh;


    public static Context context;

    private final String RSS_link="http://wku-connection.000webhostapp.com/feed";
    private final String RSS_link_json="https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pullTorefresh=(SwipeRefreshLayout)findViewById(R.id.pullToRefresh);

        context=this;

        //pull to refresh
        pullTorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadurl();
                pullTorefresh.setRefreshing(false);
            }
        });


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }  if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WKU-Connection");
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadurl();


    }

    private void loadurl() {

        @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> loadnews= new AsyncTask<String, String, String>() {

            ProgressDialog mdialog= new ProgressDialog(MainActivity.this);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mdialog.setMessage("please wait...");
                mdialog.show();
                //toolbar.setTitle("updating....");

            }

            @Override
            protected String doInBackground(String... strings) {

                String result;
                HttpHandler http= new HttpHandler();
                result= http.GetHttpData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mdialog.dismiss();
                //toolbar.setTitle("WKU-Connection");
                //

                Gson gson=new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();

               RssObject rssObject = gson.fromJson(s,RssObject.class);
                MyAdapter adapter = new MyAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        };
        StringBuilder get_url_result= new StringBuilder(RSS_link_json);
        get_url_result.append(RSS_link);
        loadnews.execute(get_url_result.toString());//request


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
