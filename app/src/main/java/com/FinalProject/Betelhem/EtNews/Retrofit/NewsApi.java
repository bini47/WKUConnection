package com.FinalProject.Betelhem.EtNews.Retrofit;

import com.FinalProject.Betelhem.EtNews.Model.RssObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {

    @GET("/json")
    Call<RssObject> fetchNews();

    @GET("/category/የሀገር-ውስጥ-ዜናዎች/json")
    Call<RssObject> fetchLocalNews();

    @GET("/category/health/json")
    Call<RssObject> fetchHealthNews();

    @GET("/category/business/json")
    Call<RssObject> fetchBusinessNews();

    @GET("/category/sport/json")
    Call<RssObject> fetchSportNews();
}
