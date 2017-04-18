package com.example.a1.projecttest.Entities;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.a1.projecttest.AppDatabase;
import com.example.a1.projecttest.R;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class CareEntity extends BaseModel {
    @PrimaryKey
    private int id;

    @Column
    private int serviceListId;

    @Column
    private String nameCare;

    public int getId() {
        return id;
    }

    public int getServiceListId() {
        return serviceListId;
    }

    public void setServiceListId(int serviceListId) {
        this.serviceListId = serviceListId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCare() {
        return nameCare;
    }

    public void setNameCare(String nameCare) {
        this.nameCare = nameCare;
    }

    public static List<CareEntity> select (){
        return SQLite.select().from(CareEntity.class)
                .queryList();
    }

    public static void insertCare(String nameCare, int serviceListId){
        SQLite.insert(CareEntity.class)
                .columns("nameCare", "serviceListId")
                .values(nameCare, serviceListId)
                .execute();
    }

    public static CareEntity selectCreationCare(Context context){
        return SQLite.select()
                .from(CareEntity.class)
                .where(CareEntity_Table.nameCare.eq(context.getString(R.string.creation_care)))
                .querySingle();
    }

    public static void deleteAll(){
        SQLite.delete().from(CareEntity.class).execute();
    }
}
