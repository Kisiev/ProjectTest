package com.example.a1.projecttest.rest;

import android.support.annotation.NonNull;

import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetServiceType;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.Models.GetUserData;

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

    public String setCoordinates (@NonNull int id, @NonNull String coordinateX, @NonNull String coordinateY) throws IOException{

        return  restClient.getProjectTestApiEdu()
                .setCoordinates(id, coordinateX, coordinateY)
                .execute().body();

    }

    public GetListUsers validUser ( @NonNull String login, @NonNull String password) throws IOException{
        return  restClient.getProjectTestApi()
                .validUser(login, password)
                .execute().body();

    }

    public List<GetServiceType> serviceType (String id) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getServiceTypeCall(id)
                .execute().body();
    }

    public String getPasswordByEmail (String email) throws IOException{
        return restClient.getProjectTestApiEdu()
                .getPassByEmail(email)
                .execute().body();
    }

    public GetUserData getUserData (String email, String password) throws IOException {
        return restClient.getProjectTestApiEdu()
                .getUserData(email, password)
                .execute().body();
    }

    public List<GetUserData> getUsersByRole (String id) throws IOException {
        return restClient.getProjectTestApiEdu()
                .getUsersByRole(id)
                .execute().body();
    }
    public GetStatusCode getStatusCode (String id,
                                        String patronymic,
                                        String surname,
                                        String name,
                                        String role) throws IOException {
        return restClient.getProjectTestApiEdu()
                .setUserData(id,
                        patronymic,
                        surname,
                        name,
                        role)
                .execute().body();
    }
}