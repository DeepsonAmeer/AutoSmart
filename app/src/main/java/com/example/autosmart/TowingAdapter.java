package com.example.autosmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Services;
import model.TowingModel;

public class TowingAdapter extends RecyclerView.Adapter<TowingAdapter.ViewHolder>{

    private ArrayList<TowingModel> towing;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Destination,Status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tow_make);
            Destination = itemView.findViewById(R.id.tow_des);
            Status = itemView.findViewById(R.id.tow_status);
        }
    }
    public void setTowing(ArrayList<TowingModel> towing){
        this.towing = towing;
    }
    public TowingAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.towing_view_item,parent,false);

        return new TowingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TowingAdapter.ViewHolder holder, int position) {

        holder.Name.setText(towing.get(position).Make);
        holder.Destination.setText(towing.get(position).Destination);
        holder.Status.setText(towing.get(position).Status);
    }

    @Override
    public int getItemCount() {
        return towing.size();
    }
}
