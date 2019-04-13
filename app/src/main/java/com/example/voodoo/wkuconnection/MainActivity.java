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
import com.example.voodoo.wkuconnection.Model.Item;
import com.example.voodoo.wkuconnection.Model.RssObject;

import com.example.voodoo.wkuconnection.Retrofit.NewsApi;
import com.example.voodoo.wkuconnection.common.Common;
import com.example.voodoo.wkuconnection.common.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    RssObject rssObject;
    SwipeRefreshLayout pullTorefresh;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    NewsApi newsApi;
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    public static Context context;


    private final String RSS_link_json="https://feed2json.org/convert?url=https%3A%2F%2Fet-news.000webhostapp.com%2Ffeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pullTorefresh=(SwipeRefreshLayout)findViewById(R.id.pullToRefresh);

        context=this;
        newsApi= Common.getApi();
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

        Call<RssObject> call = newsApi.fetchNews();
        call.enqueue(new Callback<RssObject>() {
            @Override
            public void onResponse(Call<RssObject> call, Response<RssObject> response) {
                displayNews(response.body().getItems());
            }

            @Override
            public void onFailure(Call<RssObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error while loading data", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void displayNews(List<Item> items) {
        MyAdapter adapter = new MyAdapter(items,MainActivity.this);
        recyclerView.setAdapter(adapter);

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
