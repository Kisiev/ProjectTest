package com.example.a1.projecttest.rest.Models;

/**
 * Created by User on 04.05.2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetAllRegionsModel implements Serializable {

    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("SOCR")
    @Expose
    private String socr;
    @SerializedName("CODE")
    @Expose
    private String code;
    @SerializedName("INDEX")
    @Expose
    private String iNDEX;
    @SerializedName("GNINMB")
    @Expose
    private String gninmb;
    @SerializedName("UNO")
    @Expose
    private String uno;
    @SerializedName("OCATD")
    @Expose
    private String ocatd;
    @SerializedName("STATUS")
    @Expose
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocr() {
        return socr;
    }

    public void setSocr(String socr) {
        this.socr = socr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getiNDEX() {
        return iNDEX;
    }

    public void setiNDEX(String iNDEX) {
        this.iNDEX = iNDEX;
    }

    public String getGninmb() {
        return gninmb;
    }

    public void setGninmb(String gninmb) {
        this.gninmb = gninmb;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getOcatd() {
        return ocatd;
    }

    public void setOcatd(String ocatd) {
        this.ocatd = ocatd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
