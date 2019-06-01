package com.FinalProject.Betelhem.EtNews;

import android.content.Context;
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

import com.FinalProject.Betelhem.EtNews.Adapter.MyNewsAdapter;
import com.FinalProject.Betelhem.EtNews.Model.Item;
import com.FinalProject.Betelhem.EtNews.Model.RssObject;
import com.FinalProject.Betelhem.EtNews.Retrofit.NewsApi;
import com.FinalProject.Betelhem.EtNews.common.Common;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthNews extends Fragment {
    View rootView;
    MyNewsAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    NewsApi newsApi;
    RecyclerView recyclerView;
    Button intenetRetry;
    SwipeRefreshLayout pullTorefresh;
    RelativeLayout  noInternet;
    Common common = new Common();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_health_news, container, false);
        getActivity().setTitle("Health");
        //INITIALIZE VIEWS
        pullTorefresh=(SwipeRefreshLayout)rootView.findViewById(R.id.pullToRefresh);
        noInternet=rootView.findViewById(R.id.no_internet);
        intenetRetry=rootView.findViewById(R.id.intenet_retry);

        //pull to refresh
        pullTorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline()) {
                    loadurl();
                    pullTorefresh.setRefreshing(false);
                }
                else{
                    Toast.makeText(getContext(), "No network detected. Can't refresh feed.", Toast.LENGTH_SHORT).show();
                    pullTorefresh.setRefreshing(false);
                }

            }
        });

        intenetRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadurl();
            }
        });

        recyclerView = rootView.findViewById(R.id.health_Recycler);
        newsApi= Common.getApi();
        loadurl();
        if (isOnline()) {
            noInternet.setVisibility(View.INVISIBLE);
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
return rootView;
    }
    private void loadurl() {

        Call<RssObject> call = newsApi.fetchHealthNews();
        call.enqueue(new Callback<RssObject>() {
            @Override
            public void onResponse(Call<RssObject> call, Response<RssObject> response) {
                List<Item> items = response.body().getItems();

                    noInternet.setVisibility(View.INVISIBLE);

                displayNews(items);
            }

            @Override
            public void onFailure(Call<RssObject> call, Throwable t) {

                    noInternet.setVisibility(View.VISIBLE);

                Toast.makeText(getContext(), "error while loading data+ "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void displayNews(List<Item> items) {
        adapter = new MyNewsAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

    }
    public boolean isOnline() {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("INTERNET-ERROR", e.getMessage());
        }
        return connected;
    }
}