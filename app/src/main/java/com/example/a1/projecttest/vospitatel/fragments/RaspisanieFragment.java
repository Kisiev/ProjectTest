package com.example.a1.projecttest.vospitatel.fragments;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.PositionSaveSession;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.DialogTutorListChildAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;

import com.example.a1.projecttest.rest.Models.GetScheduleStatusesByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.RecyclerTouchListener;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@EFragment
public class RaspisanieFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    Dialog dialog;
    GetStatusCode getStatusCode;
    List<GetKidsByGroupIdModel> getKidsByGroupIdModels;
    List<GetScheduleListModel> getScheduleListModels;
    PositionSaveSession session;
    ImageView low;
    ImageView medium;
    ImageView high;
    ImageView commentImageSend;
    Typeface typeface;
    String statusForComment;
    List<GetStatusKidModel> getStatusKidModels;
    List<GetScheduleStatusesByGroupIdModel> getStatusesGroup;
    int positionSchedule = 0;
    UserLoginSession userLoginSession;
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            loadUsersByRole();
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raspisanie_fragment, container, false);
        userLoginSession = new UserLoginSession(getActivity());
        dialog = new Dialog(getActivity());
        session = new PositionSaveSession(getActivity());
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/OpenSans-Regular.ttf");
        thread.start();
        dialog.setContentView(R.layout.list_child_for_tutor_dialog);
        recyclerView = (RecyclerView) view.findViewById(R.id.raspisanie_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
        // loadServicesForTutor();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!dialog.isShowing()) {
                    positionSchedule = position;
                    threadStatuses();
                    showDialog();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    public void threadStatuses(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getStatuses();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getStatuses(){
        RestService restService = new RestService();
        UserLoginSession session = new UserLoginSession(getActivity());
        try {
            getStatusesGroup = restService.getGroupStatuses(session.getTutorGroupId(), getScheduleListModels.get(positionSchedule).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersByRole(){
        RestService restService = new RestService();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        try {
            getKidsByGroupIdModels = restService.getKidsByGroupIdModels(userLoginSession.getTutorGroupId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            getScheduleListModels = restService.getScheduleListModel(userLoginSession.getTutorGroupId(), String.valueOf(day == 1?7:day - 1));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  /*  @Background
    public void loadServicesForTutor () {
        getLoaderManager().restartLoader(ConstantsManager.ID_LOADER, null, new LoaderManager.LoaderCallbacks<List<ChildStatusEntity>>() {

            @Override
            public Loader<List<ChildStatusEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ChildStatusEntity>> loader = new AsyncTaskLoader<List<ChildStatusEntity>>(getActivity()) {
                    @Override
                    public List<ChildStatusEntity> loadInBackground() {
                        return ChildStatusEntity.selectChilds();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ChildStatusEntity>> loader, List<ChildStatusEntity> data) {
                VospitannikAdapter adapter = new VospitannikAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildStatusEntity>> loader) {

            }
        });
    }*/

    public void showDialog() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Button backButton = (Button) dialog.findViewById(R.id.back_button_dialog);
        TextView header = (TextView) dialog.findViewById(R.id.header_text_dialog);
        header.setTypeface(typeface);
        backButton.setOnClickListener(this);

        backButton.setTypeface(typeface);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_list_child_for_tutor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DialogTutorListChildAdapter(getKidsByGroupIdModels, getStatusesGroup, getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                session.saveRecyclerViewPositions(session.getPosSchedule(), session.getIdSchedule(), position, Integer.parseInt(getKidsByGroupIdModels.get(position).getId()));
                low = (ImageView) view.findViewById(R.id.low_smile_image_dialog);
                medium = (ImageView) view.findViewById(R.id.medium_smile_image_dialog);
                high = (ImageView) view.findViewById(R.id.high_smile_image_dialog);
                commentImageSend = (ImageView) view.findViewById(R.id.send_message_to_parent);
                low.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        low.setImageResource(R.drawable.ic_sentiment_dissatisfied_red_24dp);
                        medium.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp);
                        high.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
                        statusForComment = "1";

                        threadStatus(getScheduleListModels.get(positionSchedule).getId(), "1", getKidsByGroupIdModels.get(position).getId(), "");
                        if (getStatusCode != null)
                            Toast.makeText(getActivity(), getStatusCode.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                });
                medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        medium.setImageResource(R.drawable.ic_sentiment_satisfied_yellow_24dp);
                        low.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
                        high.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
                        statusForComment = "2";
                        threadStatus(getScheduleListModels.get(positionSchedule).getId(), "2", getKidsByGroupIdModels.get(position).getId(), "");
                        if (getStatusCode != null)
                            Toast.makeText(getActivity(), getStatusCode.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                });
                high.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        high.setImageResource(R.drawable.ic_sentiment_very_satisfied_green_24dp);
                        low.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
                        medium.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp);
                        statusForComment = "3";
                        threadStatus(getScheduleListModels.get(positionSchedule).getId(), "3", getKidsByGroupIdModels.get(position).getId(), "");
                        if (getStatusCode != null)
                            Toast.makeText(getActivity(), getStatusCode.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                });
                commentImageSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCommentDialog(statusForComment, position);
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button_dialog:
                dialog.dismiss();
                break;
        }
    }

    public void threadStatus(final String scheduleId, final String statusId, final String userId, final String comment){
        Thread statusThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getStatus(scheduleId, statusId, userId, comment);
            }
        });
        statusThread.start();
        try {
            statusThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getStatus(String scheduleId, String statusId, String userId, String comment){
        RestService restService = new RestService();
        try {
            getStatusCode = restService.getStatusForSetStatus(scheduleId, statusId, userId, comment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCommentDialog(final String status, final int pos){
        final Dialog commentDialog = new Dialog(getActivity());
        commentDialog.setContentView(R.layout.comments_for_status_dialog);
        commentDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Button okButton = (Button) commentDialog.findViewById(R.id.ok_button_comment);
        Button cancelButton = (Button) commentDialog.findViewById(R.id.cancel_comment_dialog);
        final EditText commentEdit = (EditText) commentDialog.findViewById(R.id.comment_edit);
        TextView header = (TextView) commentDialog.findViewById(R.id.header_comments_status);
        okButton.setTypeface(typeface);
        cancelButton.setTypeface(typeface);
        commentEdit.setTypeface(typeface);
        header.setTypeface(typeface);

        commentDialog.show();
        dialog.dismiss();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadStatus(getScheduleListModels.get(positionSchedule).getId(), status == null ? getStatusesGroup.get(pos).getStatusId():status, getKidsByGroupIdModels.get(pos).getId(), commentEdit.getText().toString());
                if (getStatusCode.getCode().equals("200")){
                    Toast.makeText(getActivity(), getStatusCode.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDialog.dismiss();
                dialog.show();
            }
        });
    }
}
