package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a1.projecttest.entities.FeedEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.FeedAdapter;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;

import org.androidannotations.annotations.EFragment;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

@EFragment(R.layout.feed_fragment)
public class FeedFragment extends Fragment implements View.OnClickListener{
    File directory;
    Dialog dialog;
    RecyclerView recyclerView;
    RecyclerView recyclerViewFeed;
    FloatingActionButton actionButton;
    Uri selectedImage;
    NavigationView navigationView;
    List<GetStatusKidModel> getStatusKidModels;
    List<GetStatusKidModel> getAllKidStatuses;
    List<GetAllKidsModel> getAllKidsModels;
    ProgressBar loadingBar;
    int pos = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        getAllKidStatuses = new ArrayList<>();
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingRecyclerBar);
        navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_circle_item);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
      //  recyclerView.setAdapter(new CircleImageAdapter(listService, getActivity()));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recycler_feed_item);
        final LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(verticalLayoutManager);
        threadFeed();

       /* if (savedInstanceState == null) {
            threadFeed();
            recyclerViewFeed.setAdapter(new FeedAdapter(getActivity(), getAllKidStatuses, getAllKidsModels));
        }
        else {
            getAllKidStatuses = (List<GetStatusKidModel>) savedInstanceState.getSerializable(ConstantsManager.FEED_ALL_STATUSES);
            getAllKidsModels = (List<GetAllKidsModel>) savedInstanceState.getSerializable(ConstantsManager.FEED_ALL_KID);
            recyclerViewFeed.setAdapter(new FeedAdapter(getActivity(), (List<GetStatusKidModel>) savedInstanceState.getSerializable(ConstantsManager.FEED_ALL_STATUSES),(List<GetAllKidsModel>) savedInstanceState.getSerializable(ConstantsManager.FEED_ALL_KID)));
        }*/
        Toast.makeText(getActivity(), "Вы зашли как пользователь: " + sharedPreferences.getString(ConstantsManager.LOGIN, ""), Toast.LENGTH_LONG).show();
        actionButton = (FloatingActionButton) view.findViewById(R.id.child_add_action_button);
        actionButton.setOnClickListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                switch (view.getId()){
                    case R.id.liner_circle_item:
                        registerForContextMenu(view);
                        pos = position;
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Calendar calendar = Calendar.getInstance();

            }
        }));


        recyclerViewFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Toast.makeText(getContext(), "adfadfa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = verticalLayoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = verticalLayoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = verticalLayoutManager.findFirstVisibleItemPosition();//какая позиция первого элемента
                if (visibleItemCount + firstVisibleItems - 5 >= totalItemCount){
                    loadingBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Пошел", Toast.LENGTH_SHORT).show();
                } else loadingBar.setVisibility(View.GONE);

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                FeedEntity.deleteAll();
                for (GetStatusKidModel i: getAllKidStatuses)
                    FeedEntity.insertIn(i.getScheduleId(), i.getStatusId(), i.getUserId(), i.getName(), i.getScheduleName(), i.getComment());
            }
        }).start();

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()){
            case R.id.liner_circle_item:
                menu.add(0, ConstantsManager.REDICTION_CONTEXT_ITEM, 0, R.string.edit_text);
                menu.add(0, ConstantsManager.DELETE_CONTEXT_ITEM, 0, R.string.delete_item);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case ConstantsManager.DELETE_CONTEXT_ITEM:
                if (pos != -1) {
                   // ChildEntity.deleteChild(ChildEntity.selectChild().get(pos).getId());

                    //MainActivity.setMenu(navigationView);
                    pos = -1;
                }
                break;
            case ConstantsManager.REDICTION_CONTEXT_ITEM:
                if(pos != -1) {
                    showDialog(true, pos);
                    pos = -1;
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case ConstantsManager.TYPE_PHOTO:
                if(resultCode == RESULT_OK){
                    selectedImage = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    EditText editText = (EditText) dialog.findViewById(R.id.path_to_photoET);
                    editText.setText(selectedImage.getPath());
                }
        }
    }

    public void threadFeed(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getFeed();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setRecyclerView();
    }

    public void getFeed(){
        RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        try {
            getAllKidsModels = restService.getKidByParentId(userLoginSession.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < getAllKidsModels.size(); i ++) {
            try {
                getStatusKidModels = restService.getStatusKidForFeedModels(getAllKidsModels.get(i).getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < getStatusKidModels.size(); j ++) {
                getAllKidStatuses.add(getStatusKidModels.get(j));
            }
        }

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < getAllKidStatuses.size(); i ++){
            for (int j = 0; j < getAllKidStatuses.size()-1; j ++){
                try {
                    if (df2.parse(getAllKidStatuses.get(j).getCompletion()).before(df2.parse(getAllKidStatuses.get(j+1).getCompletion()))){
                        String setId = getAllKidStatuses.get(j).getId();
                        String setComment = getAllKidStatuses.get(j).getComment();
                        String setCompletion = getAllKidStatuses.get(j).getCompletion();
                        String setName = getAllKidStatuses.get(j).getName();
                        String setScheduleId = getAllKidStatuses.get(j).getScheduleId();
                        String setScheduleName = getAllKidStatuses.get(j).getScheduleName();
                        String setStatusId = getAllKidStatuses.get(j).getStatusId();
                        String setUserId = getAllKidStatuses.get(j).getUserId();

                        getAllKidStatuses.get(j).setId(getAllKidStatuses.get(j+1).getId());
                        getAllKidStatuses.get(j).setComment(getAllKidStatuses.get(j+1).getComment());
                        getAllKidStatuses.get(j).setCompletion(getAllKidStatuses.get(j+1).getCompletion());
                        getAllKidStatuses.get(j).setName(getAllKidStatuses.get(j+1).getName());
                        getAllKidStatuses.get(j).setScheduleId(getAllKidStatuses.get(j+1).getScheduleId());
                        getAllKidStatuses.get(j).setScheduleName(getAllKidStatuses.get(j+1).getScheduleName());
                        getAllKidStatuses.get(j).setStatusId(getAllKidStatuses.get(j+1).getStatusId());
                        getAllKidStatuses.get(j).setUserId(getAllKidStatuses.get(j+1).getUserId());

                        getAllKidStatuses.get(j+1).setId(setId);
                        getAllKidStatuses.get(j+1).setComment(setComment);
                        getAllKidStatuses.get(j+1).setCompletion(setCompletion);
                        getAllKidStatuses.get(j+1).setName(setName);
                        getAllKidStatuses.get(j+1).setScheduleId(setScheduleId);
                        getAllKidStatuses.get(j+1).setScheduleName(setScheduleName);
                        getAllKidStatuses.get(j+1).setStatusId(setStatusId);
                        getAllKidStatuses.get(j+1).setUserId(setUserId);

                    }
                } catch (ParseException e) {
                e.printStackTrace();
                }

            }
        }


    }
    public void setRecyclerView(){
        recyclerViewFeed.setAdapter(new FeedAdapter(getActivity(), getAllKidStatuses, getAllKidsModels));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ConstantsManager.FEED_ALL_KID, (Serializable) getAllKidsModels);
        outState.putSerializable(ConstantsManager.FEED_ALL_STATUSES, (Serializable) getAllKidStatuses);
    }

    public void showDialog(final boolean isRediction, final int position) {

        dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.add_child_in_parent_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final EditText nameEdit = (EditText) dialog.findViewById(R.id.name_childET);
        final EditText path = (EditText) dialog.findViewById(R.id.path_to_photoET);
        Button addButton = (Button) dialog.findViewById(R.id.add_child_buttonBT);
        final Button photoButton = (Button) dialog.findViewById(R.id.add_photo_buttonBT);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancel_buttonBT);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_photo_buttonBT:
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, ConstantsManager.TYPE_PHOTO);
                break;
            case R.id.child_add_action_button:
                showDialog(false, -1);
                break;
        }
    }

}
