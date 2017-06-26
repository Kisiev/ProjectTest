package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a1.projecttest.LoginActivity;
import com.example.a1.projecttest.LoginActivity_;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetAttendanceModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.example.a1.projecttest.zavedushaia.adapters.TutorAdapter;
import com.example.a1.projecttest.zavedushaia.adapters.TutorSpinnerAdapter;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TutorFragment extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    List<GetAllTutors> getAllTutorsesOn;
    GetKinderGarten getKinderGartenOn;
    Button addTutorButton;
    Dialog dialog;
    TextView headerDialog;
    Spinner tutorListSpinner;
    Button addTutorDialogButton;
    Button cancelTutorDialogButton;
    Typeface typeface;
    TutorSpinnerAdapter spinnerAdapter;
    Observable<List<GetAllTutors>> getAllTutorObserverable;
    Observable<GetKinderGarten> getKinderGartenObservable;
    private Paint p = new Paint();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_zav_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutor);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/SF-UI-Text-Regular.ttf");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addTutorButton = (Button) view.findViewById(R.id.add_tutor_button);
        addTutorButton.setOnClickListener(this);
        addTutorButton.setTypeface(typeface);
        getTutors();
       // setUpItemTouchHelper();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                view.findViewById(R.id.edit_button_tutor).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("1111111", "click");
                    }
                });
                view.findViewById(R.id.delete_button_group).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("1111111", "delete");
                    }
                });
                view.findViewById(R.id.edit_button_group).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("1111111", "edit");
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void getTutors(){
        final RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        try {
            getKinderGartenObservable = restService.getKinderGartenTutorObserver(userLoginSession.getID());
            getKinderGartenObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetKinderGarten>() {
                        @Override
                        public void onCompleted() {
                            try {
                                getAllTutorObserverable = restService.getAllTutorsObserver(getKinderGartenOn.getId());
                                getAllTutorObserverable.subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<GetAllTutors>>() {
                                            @Override
                                            public void onCompleted() {
                                                recyclerView.setAdapter(new TutorAdapter(getAllTutorsesOn, getActivity()));
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(List<GetAllTutors> getAllTutorses) {
                                                getAllTutorsesOn = getAllTutorses;
                                            }
                                        });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetKinderGarten getKinderGarten) {
                            getKinderGartenOn = getKinderGarten;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTutorsWithoutGroups(){

    }

    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            Drawable background;
            Drawable xMark;
            ImageView imageView;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                imageView = new ImageView(getActivity());
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(getActivity(), R.drawable.ic_access_time_black_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) getActivity().getResources().getDimension(R.dimen.mr_controller_volume_group_list_item_icon_size);
                imageView.setImageDrawable(xMark);

                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }



            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                int position = viewHolder.getAdapterPosition();
                return super.getSwipeDirs(recyclerView, viewHolder);

            }



            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                View view = viewHolder.itemView;
            }



            @Override

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                View itemView = viewHolder.itemView;
                if (viewHolder.getAdapterPosition() == -1) {
                    return;
                }
                if (!initiated) {
                    init();
                }

                // draw red background
                if (dX < -300) {
                    dX = -300;
                }
                    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    background.draw(c);
                    Button button = new Button(getActivity());
                    button.setBackground(background);
                    button.setText("dasjfdklsfjdklsj");

                    // draw x mark

                    int itemHeight = itemView.getBottom() - itemView.getTop();
                    int intrinsicWidth = xMark.getIntrinsicWidth();
                    int intrinsicHeight = xMark.getIntrinsicWidth();

                    int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                    int xMarkRight = itemView.getRight() - xMarkMargin;
                    int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                    int xMarkBottom = xMarkTop + intrinsicHeight;

                    xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);
                    xMark.draw(c);




                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }



        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }




    public void showDialogAddTutor(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_tutor_in_kindergarten_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        headerDialog = (TextView) dialog.findViewById(R.id.header_add_tutor_in_kin_dialog);
        tutorListSpinner = (Spinner) dialog.findViewById(R.id.tutor_without_kindergarten_spinner);
        addTutorDialogButton = (Button) dialog.findViewById(R.id.add_tutor_in_kin_button);
        cancelTutorDialogButton = (Button) dialog.findViewById(R.id.cancel_button_add_tutor_in_kin);

        headerDialog.setTypeface(typeface);
        addTutorDialogButton.setTypeface(typeface);
        cancelTutorDialogButton.setTypeface(typeface);

       // spinnerAdapter = new TutorSpinnerAdapter(getActivity(), list);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //tutorListSpinner.setAdapter(spinnerAdapter);
        addTutorDialogButton.setOnClickListener(this);
        cancelTutorDialogButton.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_tutor_button:
                showDialogAddTutor();
                break;
            case R.id.cancel_button_add_tutor_in_kin:
                dialog.dismiss();
                break;
            case R.id.add_tutor_in_kin_button:
                break;
        }
    }
}
