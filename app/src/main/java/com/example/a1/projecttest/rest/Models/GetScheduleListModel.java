package com.example.a1.projecttest.rest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetScheduleListModel {

    @SerializedName("serviceId")
    private String serviceId;
    @SerializedName("day")
    private String day;
    @SerializedName("timeFrom")
    private String timeFrom;
    @SerializedName("timeTo")
    private String timeTo;
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("isFinished")
    private String isFinished;
    @SerializedName("name")
    private String name;

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

}