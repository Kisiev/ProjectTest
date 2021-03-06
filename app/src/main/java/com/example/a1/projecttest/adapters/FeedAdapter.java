package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.entities.FeedEntity;
import com.example.a1.projecttest.entities.GetAllKidEntity;
import com.example.a1.projecttest.fragments.FeedFragment;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static ru.yandex.core.CoreApplication.getActivity;
import static ru.yandex.core.CoreApplication.readRootForSharedFromGlobalSettings;


public class FeedAdapter extends XRecyclerView.Adapter<FeedAdapter.FeedHolder> {

    Context context;
    List<FeedEntity> getStatusKidModels;
    List<GetAllKidEntity> getAllKidsModels;

    @NonNull
    private String getWordMinutes(String difference, int i){
        int word = Integer.valueOf(difference);
        String word_1 = "";
        String word_2 = "";
        String word_3 = "";
        switch (i){
            case 3:
                word_1 = " час назад";
                word_2 = " часа назад";
                word_3 = " часов назад";
                break;
            case 4:
                word_1 = " минуту назад";
                word_2 = " минуты назад";
                word_3 = " минут назад";
                break;
            case 5:
                word_1 = " секунду назад";
                word_2 = " секунды назад";
                word_3 = " секунд назад";
                break;
        }
        if (word == 1)
            return word_1;
        else if (word > 1 && word < 5)
            return word_2;
        else if (word >= 5 && word <= 20)
            return word_3;
        else if (word > 20){
            switch (difference.substring(1, 2)){
                case "1":
                    return word_1;
                case "2":
                    return word_2;
                case "3":
                    return word_2;
                case "4":
                    return word_2;
                case "5":
                    return word_3;
                case "6":
                    return word_3;
                case "7":
                    return word_3;
                case "8":
                    return word_3;
                case "9":
                    return word_3;
                case "0":
                    return word_3;
            }
        } else return word_3;
        return "";
    }

    private String parserDate(String date) {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String now = df2.format(calendar.getTime());
        String [] parsebleDate = date.split("[-\\ \\:]");
        String [] parsebleNow = now.split("[-\\ \\:]");
        long sumSecondNow = 0;
        long sumSecondDate = 0;
        long differenceSum = 0;
        String returnObject = "";

        sumSecondNow += Integer.valueOf(parsebleNow[2]) * 60 * 60 * 24;
        sumSecondNow += Integer.valueOf(parsebleNow[3]) * 60 * 60;
        sumSecondNow += Integer.valueOf(parsebleNow[4]) * 60;
        sumSecondNow += Integer.valueOf(parsebleNow[5]);

        sumSecondDate += Integer.valueOf(parsebleDate[2]) * 60 * 60 * 24;
        sumSecondDate += Integer.valueOf(parsebleDate[3]) * 60 * 60;
        sumSecondDate += Integer.valueOf(parsebleDate[4]) * 60;
        sumSecondDate += Integer.valueOf(parsebleDate[5]);

        differenceSum = sumSecondNow - sumSecondDate;

        if (differenceSum / 60/ 60 /24 > 0)
            returnObject = date;
        else if (differenceSum / 60 /60 > 0 && differenceSum / 60 /60 < 24)
            returnObject = differenceSum / 60 /60 + getWordMinutes(String.valueOf(differenceSum / 60 /60), 3);
        else if (differenceSum / 60 > 0 && differenceSum / 60 < 60)
            returnObject = differenceSum / 60 + getWordMinutes(String.valueOf(differenceSum / 60), 4);
        else if (differenceSum >= 0 && differenceSum < 60)
            returnObject = differenceSum + getWordMinutes(String.valueOf(differenceSum), 5);
        else returnObject = date;
        if (!parsebleDate[0].equals(parsebleNow[0]) || !parsebleDate[1].equals(parsebleNow[1]))
            returnObject = date;
        return returnObject;
    }

    public FeedAdapter (Context context, List<FeedEntity> getStatusKidModels, List<GetAllKidEntity> getAllKidsModels) {
        this.getStatusKidModels = getStatusKidModels;
        this.getAllKidsModels = getAllKidsModels;
        this.context = context;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_fragment_item, parent, false);
        return new FeedHolder(view, context);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {

        for (int j = 0; j < getAllKidsModels.size(); j ++) {
            if (getStatusKidModels.get(position).getUserId().equals(getAllKidsModels.get(j).get_id())) {
                holder.nameChildTV.setText(getAllKidsModels.get(j).getName());
                holder.serviceNameTV.setText(getStatusKidModels.get(position).getScheduleName());
                //FeedFragment.getPhotoFeed(context, holder.imageComment, ConstantsManager.BASE_NAME_EDU + getStatusKidModels.get(position).getImageUrl());
                if (getStatusKidModels.get(position).getCompletion() != null)
                    holder.dateTV.setText(parserDate(getStatusKidModels.get(position).getCompletion()));
                switch (getStatusKidModels.get(position).getStatusId()){
                    case "1":
                        holder.statusImage.setImageResource(R.mipmap.lowstatus);
                        break;
                    case "2":
                        holder.statusImage.setImageResource(R.mipmap.mediumstatus);
                        break;
                    case "3":
                        holder.statusImage.setImageResource(R.mipmap.highstatus);
                        break;
                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return getStatusKidModels.size();
    }

    class FeedHolder extends XRecyclerView.ViewHolder {
        TextView nameChildTV;
        TextView serviceNameTV;
        ImageView statusImage;
        TextView dateTV;
        CardView cardView;
        ImageView imageComment;
        public FeedHolder(View itemView, Context context) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Bold.ttf");
            Typeface typefaceReg = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
            nameChildTV = (TextView) itemView.findViewById(R.id.name_childTV);
            serviceNameTV = (TextView) itemView.findViewById(R.id.service_nameTV);
            cardView = (CardView) itemView.findViewById(R.id.card_feed_parent);
            dateTV = (TextView) itemView.findViewById(R.id.date_feed_add_tv);
            statusImage = (ImageView) itemView.findViewById(R.id.status_image_view);
            imageComment = (ImageView) itemView.findViewById(R.id.image_comment);
            dateTV.setTypeface(typefaceReg);
            nameChildTV.setTypeface(typeface);
            serviceNameTV.setTypeface(typefaceReg);
        }
    }

}
