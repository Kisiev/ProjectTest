package com.example.a1.projecttest.rest;

import com.example.a1.projecttest.utils.ConstantsManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RestClient {
    public final static String BASE_NAME = "http://agamidzk.beget.tech";


    private ProjectTestApi projectTestApi;
    private ProjectTestApi projectTestApiEdu;

    public RestClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(ConstantsManager.BASE_NAME_EDU)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        projectTestApiEdu = retrofit1.create(ProjectTestApi.class);

        projectTestApi = retrofit.create(ProjectTestApi.class);
    }



    public ProjectTestApi getProjectTestApi() {
        return projectTestApi;
    }
    public ProjectTestApi getProjectTestApiEdu() {
        return projectTestApiEdu;
    }

}