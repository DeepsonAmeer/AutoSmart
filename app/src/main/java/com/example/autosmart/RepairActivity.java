package com.example.autosmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import model.GuideList;
import model.Notification;


public class RepairActivity extends AppCompatActivity {

    EditText search;
    RecyclerView recyclerView;
    ArrayList<GuideList> guideLists;
    GuideListAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        search = (EditText) findViewById(R.id.repair_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        recyclerView = findViewById(R.id.guideListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(RepairActivity.this));
        adapter = new GuideListAdapter(this);
        guideLists = new ArrayList<>();
        adapter.setGuideList(guideLists);
        recyclerView.setAdapter(adapter);
        getData();
        adapter.notifyDataSetChanged();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String filter) {
        // on below line we are creating a new array list
        // for storing our filtered data.
        ArrayList<GuideList> filteredlist = new ArrayList<>();
        // running a for loop to search the data from our array list.
        for (GuideList item : guideLists) {
            // on below line we are getting the item which are
            // filtered and adding it to filtered list.
            if (item.getServicename().toLowerCase().contains(filter.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        // on below line we are checking
        // weather the list is empty or not.
        if (filteredlist.isEmpty()) {
            // if list is empty we are displaying a toast message.
            Toast.makeText(this, "No Repair found..", Toast.LENGTH_SHORT).show();
        } else {
            // on below line we are calling a filter
            // list method to filter our list.
            adapter.filterList(filteredlist);
        }
        // calling get data method to get data from API.



    }

    void getData(){
        String url = getResources().getString(R.string.endpoint)+"/api/Guides";

        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject repair = response.getJSONObject(i);
                                String n = repair.getString("serviceName");
                                String m = repair.getString("carMake");
                                String mm = repair.getString("carModel");
                                String id = repair.getString("guideId");
                                GuideList li = new GuideList();
                                li.setServicename(n);
                                li.setCarmake(m);
                                li.setCarmodel(mm);
                                li.setId(id);
                                guideLists.add(li);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                            }
                        //Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RepairActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent i = new Intent(RepairActivity.this, Home.class);
                        startActivity(i);
                        RepairActivity.this.finish();
                    }
                },500);
            }
        });
        queue.add(request);
    }
}