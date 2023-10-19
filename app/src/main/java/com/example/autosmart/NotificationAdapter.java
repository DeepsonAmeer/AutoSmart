package com.example.autosmart;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<Notification> notifications;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView make,engineer,status;
        LinearLayout sc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            make = itemView.findViewById(R.id.Make_txt);
            status = itemView.findViewById(R.id.status_txt);
            sc = itemView.findViewById(R.id.recycler_linear);
        }
    }
    public void setNotification(ArrayList<Notification> notifications){
        this.notifications = notifications;
    }
    public NotificationAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row_item,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.make.setText(notifications.get(position).getMake());
        holder.status.setText(notifications.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
