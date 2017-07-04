package com.example.a1.projecttest.zavedushaia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.entities.GetAllKidEntity;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.zavedushaia.adapters.ChildListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static ru.yandex.core.CoreApplication.getActivity;

@EActivity(R.layout.child_list_fragment)
public class ChildListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    TextView header;
    Dialog addKidDialog;
    EditText nameEdit;
    EditText surnameEdit;
    EditText patronymicEdit;
    EditText idParentEdit;
    Typeface typeface;
    Observable<List<GetKidsByGroupIdModel>> getAllKidByGroup;
    List<GetKidsByGroupIdModel> kidListModel;
    Observable<GetStatusCode> getStatus;
    GetStatusCode getStatusCodeOn;
    @AfterViews
    void main(){

        typeface = Typeface.createFromAsset(getAssets(), "font/SF-UI-Text-Regular.ttf");
        recyclerView = (RecyclerView) findViewById(R.id.child_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getKidByGroup();

    }

    public void getKidByGroup(){
        RestService restService = new RestService();
        try {
            getAllKidByGroup = restService.getKidsByGroupIdObserber(String.valueOf(getIntent().getSerializableExtra(ConstantsManager.GROUP_INTENT)));
            getAllKidByGroup.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GetKidsByGroupIdModel>>() {
                        @Override
                        public void onCompleted() {
                            recyclerView.setAdapter(new ChildListAdapter(getApplicationContext(), kidListModel));
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<GetKidsByGroupIdModel> getKidsByGroupIdModels) {
                            kidListModel = getKidsByGroupIdModels;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void showDialogAddKid(){
        addKidDialog = new Dialog(this);
        addKidDialog.setContentView(R.layout.add_kids_in_group_dialog);
        addKidDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        header = (TextView) addKidDialog.findViewById(R.id.header_dialog_add_kid);
        nameEdit = (EditText) addKidDialog.findViewById(R.id.name_child_add_kid_dialog);
        surnameEdit = (EditText) addKidDialog.findViewById(R.id.surname_child_add_kid_dialog);
        patronymicEdit = (EditText) addKidDialog.findViewById(R.id.patronimic_child_add_kid_dialog);
        idParentEdit = (EditText) addKidDialog.findViewById(R.id.id_parent_child_add_kid_dialog);
        Button addKidButton = (Button) addKidDialog.findViewById(R.id.add_button_child_in_group);
        Button cancelButton = (Button) addKidDialog.findViewById(R.id.cancel_button_child_in_group);
        addKidButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        header.setTypeface(typeface);
        nameEdit.setTypeface(typeface);
        surnameEdit.setTypeface(typeface);
        patronymicEdit.setTypeface(typeface);
        addKidButton.setTypeface(typeface);
        cancelButton.setTypeface(typeface);

        header.setText(header.getText() +" "+ getIntent().getSerializableExtra(ConstantsManager.GROUP_NAME_INTENT));


        addKidDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        addKidDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_in_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item_menu:
                showDialogAddKid();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addKidInGroup(){
        RestService restService = new RestService();
        try {
            getStatus = restService.setKidInGroupObserver(idParentEdit.getText().toString(),
                    nameEdit.getText().toString(),
                    surnameEdit.getText().toString(),
                    patronymicEdit.getText().toString(),
                    String.valueOf(getIntent().getSerializableExtra(ConstantsManager.GROUP_INTENT)));
            getStatus.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetStatusCode>() {
                        @Override
                        public void onCompleted() {
                            if (getStatusCodeOn.getCode().equals("200")){
                                Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                            } else if (getStatusCodeOn.getCode().equals("200")){
                                Toast.makeText(getApplicationContext(), getStatusCodeOn.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetStatusCode getStatusCode) {
                            getStatusCodeOn = getStatusCode;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_button_child_in_group:
                addKidInGroup();
                break;
        }
    }
}
