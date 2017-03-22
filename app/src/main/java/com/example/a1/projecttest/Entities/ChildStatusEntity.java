package com.example.a1.projecttest.Entities;

import android.app.Application;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Table(database = AppDatabase.class, insertConflict = ConflictAction.REPLACE)
public class ChildStatusEntity extends BaseModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public int getTimeChecked() {
        return timeChecked;
    }

    public void setTimeChecked(int timeChecked) {
        this.timeChecked = timeChecked;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @PrimaryKey
    private int id;

    @Column
    private String serviceName;

    @Column
    private String timeIn;

    @Column
    private String timeOut;

    @Column
    private int timeChecked;

    @Column
    private String comments;

    public static void insert (String serviceName, String timeIn, String timeOut, int timeChecked, String comments) {
        SQLite.insert(ChildStatusEntity.class)
                .columns("serviceName", "timeIn", "timeOut", "timeChecked", "comments")
                .values(serviceName, timeIn, timeOut, timeChecked, comments)
                .execute();

    }

    public static List<ChildStatusEntity> selectChilds(){
        return SQLite.select().from(ChildStatusEntity.class)
                .queryList();
    }

}
