package com.FinalProject.Betelhem.EtNews;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
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


    private final String RSS_link_json="https://feed2json.org/convert?url=https%3A%2F%2Fet-news.000webhostapp.com%2Ffeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pullTorefresh=(SwipeRefreshLayout)findViewById(R.id.pullToRefresh);

        context=this;
        NewsDbHelper dbHelper = new NewsDbHelper(context);
        mDatabase=dbHelper.getWritableDatabase();

        adapter = new MyAdapter(getAllNews(),this);

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
        toolbar.setTitle("ET-News");
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(isNetworkAvailable())
        loadurl();
        else{
            adapter.swapCursor(getAllNews());
            displayNews();
        }


    }

    private void loadurl() {

        Call<RssObject> call = newsApi.fetchNews();
        call.enqueue(new Callback<RssObject>() {
            @Override
            public void onResponse(Call<RssObject> call, Response<RssObject> response) {
                List<Item> items = response.body().getItems();
                //ADDING LOADED DATA TO THE DATABASE
                for (Item item:  items){
                    cv.put(NewsEntery.COLUMN_TITlE, item.getTitle());
                    cv.put(NewsEntery.COLUMN_DATE, item.getDate_published());
                    cv.put(NewsEntery.COLUMN_URL, item.getUrl());
                    try{
                        cv.put(NewsEntery.COLUMN_TAG, item.getTags().get(0));
                    }catch (NullPointerException e){
                        cv.put(NewsEntery.COLUMN_TAG, "uncatagorized");
                    }
                    cv.put(NewsEntery.COLUMN_CONTENT, common.formatString(item.getContent_text()));
                    cv.put(NewsEntery.COLUMN_ISREADEN, 0);

                    mDatabase.insert(NewsEntery.TABLE_NAME, null, cv);

                }

                adapter.swapCursor(getAllNews());

                displayNews();
            }

            @Override
            public void onFailure(Call<RssObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error while loading data+ "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void displayNews() {
          adapter = new MyAdapter(getAllNews(), context);
        recyclerView.setAdapter(adapter);

    }
    private Cursor getAllNews(){
        return mDatabase.query(
                NewsEntery.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
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
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo= connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();

    }
}
