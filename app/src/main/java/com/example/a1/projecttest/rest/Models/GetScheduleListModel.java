package com.example.a1.projecttest.rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetScheduleListModel implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("serviceId")
    @Expose
    private String serviceId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("timeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("timeTo")
    @Expose
    private String timeTo;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("isFinished")
    @Expose
    private String isFinished;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serviceTypeId")
    @Expose
    private String serviceTypeId;
    @SerializedName("serviceListId")
    @Expose
    private String serviceListId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceListId() {
        return serviceListId;
    }

    public void setServiceListId(String serviceListId) {
        this.serviceListId = serviceListId;
    }

}