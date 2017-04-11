package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a1.projecttest.Entities.ChildEntity;
import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.CircleImageAdapter;
import com.example.a1.projecttest.adapters.FeedAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

@EFragment
public class FeedFragment extends Fragment implements View.OnClickListener{
    File directory;
    Dialog dialog;
    RecyclerView recyclerView;
    RecyclerView recyclerViewFeed;
    FloatingActionButton actionButton;
    Uri selectedImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        List<String> listService = new ArrayList<>();
        listService.add("Иванов А.В");
        listService.add("Иванов В.П");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_circle_item);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
      //  recyclerView.setAdapter(new CircleImageAdapter(listService, getActivity()));
        loadChildList();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recycler_feed_item);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(verticalLayoutManager);
        recyclerViewFeed.setAdapter(new FeedAdapter(listService));

        Toast.makeText(getActivity(), "Вы зашли как пользователь: " + sharedPreferences.getString(ConstantsManager.LOGIN, ""), Toast.LENGTH_LONG).show();
        actionButton = (FloatingActionButton) view.findViewById(R.id.child_add_action_button);
        actionButton.setOnClickListener(this);
        return view;
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


    public void showDialog() {

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_child_in_parent_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final EditText nameEdit = (EditText) dialog.findViewById(R.id.name_childET);
        Button addButton = (Button) dialog.findViewById(R.id.add_child_buttonBT);
        final Button photoButton = (Button) dialog.findViewById(R.id.add_photo_buttonBT);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancel_buttonBT);
        photoButton.setOnClickListener(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildEntity.insertChild(nameEdit.getText().toString(), selectedImage.toString());
                loadChildList();
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
                showDialog();
                break;
        }
    }

    @Background
    public void loadChildList() {
        getLoaderManager().restartLoader(ConstantsManager.ID_LOADER, null, new LoaderManager.LoaderCallbacks<List<ChildEntity>>() {

            @Override
            public Loader<List<ChildEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ChildEntity>> loader = new AsyncTaskLoader<List<ChildEntity>>(getActivity()) {
                    @Override
                    public List<ChildEntity> loadInBackground() {
                        return ChildEntity.selectChild();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ChildEntity>> loader, List<ChildEntity> data) {
                CircleImageAdapter adapter = new CircleImageAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildEntity>> loader) {

            }
        });
    }

}
