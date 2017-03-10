package com.example.a1.projecttest.rest;

import android.support.annotation.NonNull;

import com.example.a1.projecttest.rest.Models.GetListUsers;

import java.io.IOException;
import java.util.List;


public final class RestService {

    private RestClient restClient;
    public RestService(){
        restClient = new RestClient();
    }

    public List<GetListUsers> viewListInMainFragmenr () throws IOException{

        return  restClient.getProjectTestApi()
                .getListModel()
                .execute().body();

    }

}