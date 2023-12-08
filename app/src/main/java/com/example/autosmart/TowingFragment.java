package com.example.autosmart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.Notification;
import model.Services;
import model.TowingModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TowingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TowingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth mAuth;
    ArrayList<TowingModel> towing;
    TowingAdapter adapter;

    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    public TowingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TowingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TowingFragment newInstance(String param1, String param2) {
        TowingFragment fragment = new TowingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_towing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.towing_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TowingAdapter(getContext());
        towing = new ArrayList<>();
        adapter.setTowing(towing);
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        getData();

        adapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
    }

    void getData(){
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();
        String url = getResources().getString(R.string.endpoint)+"/api/Towings/"+email;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject repair = response.getJSONObject(i);
                                String make = repair.getString("make");
                                String description = repair.getString("description");
                                String status = repair.getString("status");
                                TowingModel t = new TowingModel();
                                t.Make = make;
                                t.Destination = description;
                                t.Status = status;
                                towing.add(t);

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
                        Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent i = new Intent(getContext(), Home.class);
                        startActivity(i);
                    }
                },500);
            }
        });
        queue.add(request);
    }
}