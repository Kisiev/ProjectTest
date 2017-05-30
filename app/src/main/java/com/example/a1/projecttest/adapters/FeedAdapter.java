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
import android.widget.Switch;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;

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


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    Context context;
    Typeface typeface;
    List<GetStatusKidModel> getStatusKidModels;
    List<GetAllKidsModel> getAllKidsModels;

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

    public FeedAdapter (Context context, List<GetStatusKidModel> getStatusKidModels, List<GetAllKidsModel> getAllKidsModels) {
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
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");

        for (int j = 0; j < getAllKidsModels.size(); j ++) {
            if (getStatusKidModels.get(position).getUserId().equals(getAllKidsModels.get(j).getId())) {
                holder.nameChildTV.setText(getAllKidsModels.get(j).getName());
                holder.serviceNameTV.setText("Закончил ''" + getStatusKidModels.get(position).getScheduleName() + "'' со статусом ''" + getStatusKidModels.get(position).getName() + "''");
                holder.nameChildTV.setTypeface(typeface);
                holder.serviceNameTV.setTypeface(typeface);
                if (getStatusKidModels.get(position).getCompletion() != null)
                    holder.dateTV.setText(parserDate(getStatusKidModels.get(position).getCompletion()));
                switch (getStatusKidModels.get(position).getStatusId()){
                    case "1":
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorRedOpacity));
                        break;
                    case "2":
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorYellowOpacity));
                        break;
                    case "3":
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorGreenOpacity));
                        break;
                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return getStatusKidModels.size();
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        TextView nameChildTV;
        TextView serviceNameTV;
        TextView dateTV;
        CardView cardView;
        public FeedHolder(View itemView, Context context) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
            nameChildTV = (TextView) itemView.findViewById(R.id.name_childTV);
            serviceNameTV = (TextView) itemView.findViewById(R.id.service_nameTV);
            cardView = (CardView) itemView.findViewById(R.id.card_feed_parent);
            dateTV = (TextView) itemView.findViewById(R.id.date_feed_add_tv);
            dateTV.setTypeface(typeface);
            nameChildTV.setTypeface(typeface);
            serviceNameTV.setTypeface(typeface);
        }
    }

}
