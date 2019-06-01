package com.FinalProject.Betelhem.EtNews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.FinalProject.Betelhem.EtNews.Adapter.MyAdapter;
import com.FinalProject.Betelhem.EtNews.Adapter.MyNewsAdapter;
import com.FinalProject.Betelhem.EtNews.Model.Item;
import com.FinalProject.Betelhem.EtNews.Model.RssObject;
import com.FinalProject.Betelhem.EtNews.Retrofit.NewsApi;
import com.FinalProject.Betelhem.EtNews.common.Common;
import com.FinalProject.Betelhem.EtNews.common.NewsContract;
import com.FinalProject.Betelhem.EtNews.common.NewsDbHelper;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNews extends Fragment {
    View rootView;
    MyAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    NewsApi newsApi;
    RecyclerView recyclerView;
    Button intenetRetry;
    RelativeLayout  noInternet;
    NewsDbHelper dbHelper;
    ContentValues cv = new ContentValues();
    SwipeRefreshLayout pullTorefresh;
    Common common = new Common();
    public static Context context;
    private SQLiteDatabase mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_all_news, container, false);
        //INITIALIZING VIEWS
        pullTorefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.pullToRefresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        context= getContext();
        getActivity().setTitle("Et News");

        // DATABASE INITIALIZATION
          dbHelper = new NewsDbHelper(context);
        mDatabase=dbHelper.getWritableDatabase();

        //API CALL SETUP
        newsApi= Common.getApi();
        noInternet=rootView.findViewById(R.id.no_internet);

        //pull to refresh
        pullTorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    loadurl();
                    pullTorefresh.setRefreshing(false);
                }
                else{
                    Toast.makeText(getContext(), "No network detected. Can't refresh feed.", Toast.LENGTH_SHORT).show();
                    pullTorefresh.setRefreshing(false);
                }

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(isNetworkAvailable()){
            loadurl();
        adapter = new MyAdapter(getAllNews(),getContext());}
        else {
            adapter = new MyAdapter(getAllNews(),getContext());
            adapter.swapCursor(getAllNews());
            displayNews();
        }
        return rootView;
    }
    private void loadurl() {

        Call<RssObject> call = newsApi.fetchNews();
        call.enqueue(new Callback<RssObject>() {
            @Override
            public void onResponse(Call<RssObject> call, Response<RssObject> response) {
                List<Item> items = response.body().getItems();
                //REMOVING THE OLD DATABASE
                dbHelper.removeDb(mDatabase);
                //ADDING LOADED DATA TO THE DATABASE
                for (Item item:  items){
                    cv.put(NewsContract.NewsEntery.COLUMN_TITlE, item.getTitle());
                    cv.put(NewsContract.NewsEntery.COLUMN_DATE, item.getDate_published());
                    cv.put(NewsContract.NewsEntery.COLUMN_URL, item.getUrl());
                    System.out.println( item.getUrl());


                    try{
                        cv.put(NewsContract.NewsEntery.COLUMN_TAG, item.getTags().get(0));
                        cv.put(NewsContract.NewsEntery.COLUMN_CONTENT, Common.formatString(item.getContent_text()));
                    }catch (NullPointerException e){
                        cv.put(NewsContract.NewsEntery.COLUMN_CONTENT,"No Content");
                        cv.put(NewsContract.NewsEntery.COLUMN_TAG, "uncatagorized");
                    }


                    cv.put(NewsContract.NewsEntery.COLUMN_ISREADEN, 0);

                    mDatabase.insert(NewsContract.NewsEntery.TABLE_NAME, null, cv);

                }

                adapter.swapCursor(getAllNews());

                displayNews();
            }

            @Override
            public void onFailure(Call<RssObject> call, Throwable t) {
                Toast.makeText(getContext(), "error while loading data+ "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void displayNews() {
        adapter = new MyAdapter(getAllNews(), context);
        recyclerView.setAdapter(adapter);

    }
    private Cursor getAllNews(){
        return mDatabase.query(
                NewsContract.NewsEntery.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo= connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();

    }

}