package com.example.autosmart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import model.GuideList;
import model.Step;

public class GuideStepActivity extends AppCompatActivity {

    TextView step, desc;
    ImageView next,back;
    String id;
    ArrayList<Step> list;
    ProgressDialog progressDialog;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_step);



        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        id = getIntent().getStringExtra("id").toString();
        //Toast.makeText(this,"the id is "+id,Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();
        step = (TextView) findViewById(R.id.step_index);
        desc = (TextView) findViewById(R.id.active_step_desc);
        next = (ImageView) findViewById(R.id.btn_next);
        back = (ImageView) findViewById(R.id.btn_back);


        step.setVisibility(View.INVISIBLE);
        desc.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        loadData();

          i = 0;


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i<list.size()-1){
                    i++;
                    step.setText(list.get(i).getStep());
                    desc.setText(list.get(i).getDesc());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>0){
                    i--;
                    step.setText(list.get(i).getStep());
                    desc.setText(list.get(i).getDesc());
                }
            }
        });
    }
    void loadData(){

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = getResources().getString(R.string.endpoint)+"/api/Guides/"+id;
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject repair = response.getJSONObject(i);
                                String p = repair.getString("stepProcess");
                                Step steps = new Step();
                                steps.step = repair.getString("step");
                                steps.desc = p;
                                list.add(steps);


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        step.setText(list.get(0).getStep());
                        desc.setText(list.get(0).getDesc());
                        step.setVisibility(View.VISIBLE);
                        desc.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GuideStepActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent i = new Intent(GuideStepActivity.this, RepairActivity.class);
                        startActivity(i);
                        GuideStepActivity.this.finish();
                    }
                },500);
            }
        });


        queue.add(request);
    }
}