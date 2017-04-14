package com.example.a1.projecttest.rest;

import android.support.annotation.NonNull;

import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetServiceType;

import java.io.IOException;
import java.util.List;

import retrofit2.http.Query;


public final class RestService {

    private RestClient restClient;
    public RestService(){
        restClient = new RestClient();
    }

    public GetListUsers viewListInMainFragmenr (@NonNull String id) throws IOException{

        return  restClient.getProjectTestApiEdu()
                .getListModel(id)
                .execute().body();

    }

    public String setCoordinates (@NonNull String id, @NonNull String coordinateX, @NonNull String coordinateY) throws IOException{

        return  restClient.getProjectTestApiEdu()
                .setCoordinates(id, coordinateX, coordinateY)
                .execute().body();

    }

    public GetListUsers validUser ( @NonNull String login, @NonNull String password) throws IOException{
        return  restClient.getProjectTestApi()
                .validUser(login, password)
                .execute().body();

    }

    public GetServiceType serviceType (@NonNull String id) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getServiceTypeCall(id)
                .execute().body();
    }

}