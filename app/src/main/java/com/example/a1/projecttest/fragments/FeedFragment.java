package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.CircleImageAdapter;
import com.example.a1.projecttest.adapters.FeedAdapter;
import com.example.a1.projecttest.utils.ConstantsManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FeedFragment extends Fragment implements View.OnClickListener{
    File directory;
    Dialog dialog;
    FloatingActionButton actionButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        List<String> listService = new ArrayList<>();
        listService.add("Иванов А.В");
        listService.add("Иванов В.П");
        actionButton = (FloatingActionButton) view.findViewById(R.id.child_add_action_button);
        actionButton.setOnClickListener(this);
        showDialog();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_circle_item);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(new CircleImageAdapter(listService, getActivity()));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        RecyclerView recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recycler_feed_item);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(verticalLayoutManager);
        recyclerViewFeed.setAdapter(new FeedAdapter(listService));

        Toast.makeText(getActivity(), "Вы зашли как пользователь: " + sharedPreferences.getString(ConstantsManager.LOGIN, ""), Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

                if (intent == null) {
                    Log.d("sadasdasda", "Intent is null");
                } else {
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d("asffasasf", "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            //ivPhoto.setImageBitmap(bitmap);
                            EditText editText = (EditText) dialog.findViewById(R.id.name_childET);
                            editText.setText(directory.getPath());
                        }
                    }
                }
    }


    public void showDialog() {

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_child_in_parent_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        createDirectory();
        final EditText nameButton = (EditText) dialog.findViewById(R.id.name_childET);
        Button photoButton = (Button) dialog.findViewById(R.id.add_child_buttonBT);
        final Button addButton = (Button) dialog.findViewById(R.id.add_photo_buttonBT);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancel_buttonBT);
        addButton.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_photo_buttonBT:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(ConstantsManager.TYPE_PHOTO));
                startActivityForResult(intent, ConstantsManager.REQUEST_CODE_PHOTO);

                break;
            case R.id.child_add_action_button:
                showDialog();
                break;
        }
    }
    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case ConstantsManager.TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
            case ConstantsManager.TYPE_VIDEO:
                file = new File(directory.getPath() + "/" + "video_"
                        + System.currentTimeMillis() + ".mp4");
                break;
        }
        Log.d("asdasdasdasd", "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyChildPhotos");
        if (!directory.exists())
            directory.mkdirs();
    }
}
