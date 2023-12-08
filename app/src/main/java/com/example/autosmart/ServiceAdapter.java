package com.example.autosmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Notification;
import model.Services;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private ArrayList<Services> services;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Description,Status,DateCreated;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.serviceMake);
            Description = itemView.findViewById(R.id.serviceDescription);
            Status = itemView.findViewById(R.id.serviceStatus);
            DateCreated = itemView.findViewById(R.id.dateCreated);
        }
    }
    public void setServices(ArrayList<Services> services){
        this.services = services;
    }
    public ServiceAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_row_item,parent,false);

        return new ServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.Name.setText(services.get(position).getName());
        holder.Description.setText(services.get(position).getDescription());
        holder.Status.setText(services.get(position).getStatus());
        holder.DateCreated.setText(services.get(position).DateCreated);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
