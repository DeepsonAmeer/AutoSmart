package com.example.autosmart;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.GuideList;
import model.Notification;

public class GuideListAdapter extends RecyclerView.Adapter<GuideListAdapter.ViewHolder> {

    private ArrayList<GuideList> guide;
    Context context;
    String id;

    public void filterList(ArrayList<GuideList> filterlist) {
        // adding filtered list to our
        // array list and notifying data set changed
        guide = filterlist;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView servicename,carmake,carmodel;
        CardView row;

        public ViewHolder(@NonNull View view){
            super(view);
            servicename = view.findViewById(R.id.guide_service_name);
            carmake = view.findViewById(R.id.guide_car_make);
            row = view.findViewById(R.id.guide_row);
        }
    }
    public void setGuideList(ArrayList<GuideList> guide){
        this.guide = guide;
    }
    public GuideListAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guidelist_row_item,parent,false);

        return new GuideListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideListAdapter.ViewHolder holder, int position) {
        holder.servicename.setText(guide.get(position).getServicename());
        holder.carmake.setText(guide.get(position).getCarmake()+", "+guide.get(position).carmodel);
        id = guide.get(position).getId();

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(),GuideStepActivity.class);

                i.putExtra("id",guide.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return guide.size();
    }
}
