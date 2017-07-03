package com.example.a1.projecttest.zavedushaia;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.entities.GetAllKidEntity;
import com.example.a1.projecttest.rest.RestService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import rx.Observable;

@EActivity(R.layout.child_list_fragment)
public class ChildListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Observable<List<GetAllKidEntity>> getAllKidByGroup;
    List<GetAllKidEntity> kidListModel;
    @AfterViews
    void main(){
        recyclerView = (RecyclerView) findViewById(R.id.child_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter();
    }

    public void getKidByGroup(){
        RestService restService = new RestService();
        //getAllKidByGroup = restService.getKidsByGroupIdObserber();
    }
}
