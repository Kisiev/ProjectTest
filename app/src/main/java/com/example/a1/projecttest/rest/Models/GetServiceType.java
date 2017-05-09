package com.example.a1.projecttest.rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetServiceType implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("serviceListId")
    @Expose
    private String serviceListId;
    @SerializedName("name")
    @Expose
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