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
    private String typeService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public static void insertService (String name) {
        SQLite.insert(ServicesEntity.class)
                .columns("typeService")
                .values(name)
                .execute();
    }

    public static List<ServicesEntity> select (){
        return SQLite.select(ServicesEntity_Table.typeService).from(ServicesEntity.class)
                .queryList();
    }

}
