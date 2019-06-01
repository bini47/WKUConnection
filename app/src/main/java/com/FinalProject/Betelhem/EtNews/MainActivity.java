package com.FinalProject.Betelhem.EtNews;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.FinalProject.Betelhem.EtNews.Adapter.MyAdapter;
import com.FinalProject.Betelhem.EtNews.Model.Item;
import com.FinalProject.Betelhem.EtNews.Model.RssObject;

import com.FinalProject.Betelhem.EtNews.Retrofit.NewsApi;
import com.FinalProject.Betelhem.EtNews.common.Common;
import com.FinalProject.Betelhem.EtNews.common.NewsDbHelper;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.FinalProject.Betelhem.EtNews.common.NewsContract.*;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    MyAdapter adapter;
    RssObject rssObject;
    SwipeRefreshLayout pullTorefresh;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    NewsApi newsApi;
    Common common = new Common();
    ContentValues cv = new ContentValues();
    private SQLiteDatabase mDatabase;
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    public static Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //starter activity

    Intent i = new Intent(MainActivity.this, Home.class);
    startActivity(i);
    finish();
    }


}
