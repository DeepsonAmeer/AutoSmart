package com.example.autosmart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.Services;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;

    public RepairFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepairFragment newInstance(String param1, String param2) {
        RepairFragment fragment = new RepairFragment();
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
        return inflater.inflate(R.layout.fragment_repair, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.repair_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ServiceAdapter adapter = new ServiceAdapter(getContext());
        ArrayList<Services> services = new ArrayList<>();

        Services services1 = new Services();
        services1.Name = "Toyota";
        services1.Description = "Engine overheating";
        services1.Status = "Ongoing";
        services1.Engineer = "SuperEngineer";
        services1.DateCreated = "02-02-2023";
        services.add(services1);

        Services services2 = new Services();
        services2.Name = "Mercedes Benz";
        services2.Description = "Making unnecessary sounds, consuming lot of fuel";
        services2.Status = "Initiated";
        services2.Engineer = "Jon Snow";
        services2.DateCreated = "05-10-2023";
        services.add(services2);
        adapter.setServices(services);
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }
}