package com.example.a1.projecttest.Entities;

import android.app.Application;
import android.view.View;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.visible;


@Table(database = AppDatabase.class)
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

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public long getTypeService() {
        return typeService;
    }

    public void setTypeService(long typeService) {
        this.typeService = typeService;
    }

    @PrimaryKey()
    public int id;

    @Column(name = "serviceName")
    public String serviceName;

    @Column(name = "timeIn")
    public Date timeIn;

    @Column(name = "timeOut")
    public Date timeOut;

    @Column (name = "typeService")
    public long typeService;

    @Column(name = "timeChecked")
    public int timeChecked;

    @Column(name = "comments")
    public String comments;

    @Column(name = "color")
    public int color;

    @Column(name = "visible")
    public int visible;

    public static void insert (String serviceName, Date timeIn, Date timeOut, long typeService, int timeChecked, String comments, int color, int visible) {
        SQLite.insert(ChildStatusEntity.class)
                .columns("serviceName", "timeIn", "timeOut", "typeService", "timeChecked", "comments", "color", "visible")
                .values(serviceName, timeIn, timeOut, typeService, timeChecked, comments, color, visible)
                .execute();
    }

    public static List<ChildStatusEntity> selectChilds(){
        return SQLite.select().from(ChildStatusEntity.class)
                .orderBy(OrderBy.fromString("timeIn"))
                .queryList();
    }

    public static List<ChildStatusEntity> selectNameService(){
        return SQLite.select(ChildStatusEntity_Table.serviceName).from(ChildStatusEntity.class)
                .queryList();
    }

}
