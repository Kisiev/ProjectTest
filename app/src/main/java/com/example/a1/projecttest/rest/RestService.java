package com.example.a1.projecttest.rest;

import android.support.annotation.NonNull;

import com.example.a1.projecttest.rest.Models.GetListUsers;

import java.io.IOException;
import java.util.List;

import retrofit2.http.Query;


public final class RestService {

    private RestClient restClient;
    public RestService(){
        restClient = new RestClient();
    }

    public GetListUsers viewListInMainFragmenr (@NonNull String id) throws IOException{

        return  restClient.getProjectTestApi()
                .getListModel(id)
                .execute().body();

    }

    public String setCoordinates (@NonNull String id, @NonNull String coordinateX, @NonNull String coordinateY) throws IOException{

        return  restClient.getProjectTestApi()
                .setCoordinates(id, coordinateX, coordinateY)
                .execute().body();

    }

}