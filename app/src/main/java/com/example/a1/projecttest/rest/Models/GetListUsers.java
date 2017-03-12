package com.example.a1.projecttest.rest.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetListUsers {

    @SerializedName("id")
    private String id;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;
    @SerializedName("coordinateX")
    private String coordinateX;
    @SerializedName("coordinateY")
    private String coordinateY;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

}