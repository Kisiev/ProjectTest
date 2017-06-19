package com.example.a1.projecttest.rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetNotificationAddToParent implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("urlParameter")
    @Expose
    private String urlParameter;
    @SerializedName("kidId")
    @Expose
    private String kidId;
    @SerializedName("parentId")
    @Expose
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlParameter() {
        return urlParameter;
    }

    public void setUrlParameter(String urlParameter) {
        this.urlParameter = urlParameter;
    }

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
