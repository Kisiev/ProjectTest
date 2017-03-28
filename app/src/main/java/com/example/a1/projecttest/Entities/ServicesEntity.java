package com.example.a1.projecttest.Entities;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

@Table(database = AppDatabase.class, insertConflict = ConflictAction.REPLACE)
public class ServicesEntity extends BaseModel {

    @PrimaryKey
    private int id;

    @Column
    private String nameService;

    @Column
    private String typeService;

    @Column
    private String directService;

    @Column
    private Date timeIn;

    @Column
    private Date timeOut;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getDirectService() {
        return directService;
    }

    public void setDirectService(String directService) {
        this.directService = directService;
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


    public static void insertService (String name, String type, String direct, Date dateIn, Date dateOut) {
        SQLite.insert(ServicesEntity.class)
                .columns("nameService", "typeService", "directService", "timeIn", "timeOut")
                .values(name, type, direct, dateIn, dateOut)
                .execute();
    }

    public static List<ServicesEntity> select (){
        return SQLite.select().from(ServicesEntity.class)
                .queryList();
    }

}
