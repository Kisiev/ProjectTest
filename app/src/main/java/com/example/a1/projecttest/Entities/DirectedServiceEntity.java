package com.example.a1.projecttest.Entities;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class DirectedServiceEntity extends BaseModel{

    @PrimaryKey
    private int id;

    @Column
    private String nameSirected;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSirected() {
        return nameSirected;
    }

    public void setNameSirected(String nameSirected) {
        this.nameSirected = nameSirected;
    }

    public static List<DirectedServiceEntity> select(){
        return SQLite.select()
                .from(DirectedServiceEntity.class)
                .queryList();
    }

    public static void insertDirect(String name){
        SQLite.insert(DirectedServiceEntity.class)
                .columns("nameSirected")
                .values(name)
                .execute();
    }
}
