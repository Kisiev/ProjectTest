package com.example.a1.projecttest.entities;

import com.example.a1.projecttest.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.Serializable;
import java.util.List;

@Table(database = AppDatabase.class)
public class GetAllKidEntity implements Serializable{
    @PrimaryKey
    private long id;
    @Column()
    private String _id;
    @Column()
    private String name;
    @Column()
    private String surname;
    @Column()
    private String patronymic;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public static List<GetAllKidEntity> selectAll(){
        return SQLite.select().from(GetAllKidEntity.class).queryList();
    }

    public static void insertIn(String _id, String name, String surname, String patronymic){
        SQLite.insert(GetAllKidEntity.class).columns("_id", "name", "surname", "patronymic")
                .values(_id, name, surname, patronymic).execute();
    }

    public static void deleteAll(){
        SQLite.delete().from(GetAllKidEntity.class).execute();
    }
}
