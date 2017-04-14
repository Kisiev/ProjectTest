package com.example.a1.projecttest.rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetServiceType {

    @SerializedName("id")
    private String id;
    @SerializedName("serviceListId")
    private String serviceListId;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceListId() {
        return serviceListId;
    }

    public void setServiceListId(String serviceListId) {
        this.serviceListId = serviceListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}