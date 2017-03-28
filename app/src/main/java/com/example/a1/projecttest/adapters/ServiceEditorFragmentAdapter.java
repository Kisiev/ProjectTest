package com.example.a1.projecttest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ServicesEntity;
import com.example.a1.projecttest.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ServiceEditorFragmentAdapter extends RecyclerView.Adapter<ServiceEditorFragmentAdapter.ServiceEditorHolder>{

    private List<ServicesEntity> servicesEntityList;
    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");
    public ServiceEditorFragmentAdapter(List<ServicesEntity> servicesEntityList) {
        this.servicesEntityList = servicesEntityList;
    }

    @Override
    public ServiceEditorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_item, parent, false);
        return new ServiceEditorHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceEditorHolder holder, int position) {
        holder.nameService.setText(servicesEntityList.get(position).getNameService());
        holder.typeService.setText(servicesEntityList.get(position).getTypeService());
        holder.directService.setText(servicesEntityList.get(position).getDirectService());
        holder.timeIn.setText(dfDate_day_time.format(servicesEntityList.get(position).getTimeIn().getTime()));
        holder.timeOut.setText(dfDate_day_time.format(servicesEntityList.get(position).getTimeOut().getTime()));
    }

    @Override
    public int getItemCount() {
        return servicesEntityList.size();
    }

    class ServiceEditorHolder extends RecyclerView.ViewHolder {

        TextView nameService;
        TextView typeService;
        TextView directService;
        EditText timeIn;
        EditText timeOut;

        public ServiceEditorHolder(View itemView) {
            super(itemView);
            nameService = (TextView) itemView.findViewById(R.id.name_service_itemTV);
            typeService = (TextView) itemView.findViewById(R.id.type_serviceTV);
            directService = (TextView) itemView.findViewById(R.id.direct_serviceTV);
            timeIn = (EditText) itemView.findViewById(R.id.sinceET);
            timeOut = (EditText) itemView.findViewById(R.id.tillET);
        }
    }
}
