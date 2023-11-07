package com.example.autosmart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestActivity extends AppCompatActivity {

    EditText make,model,fault,location;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);


        make = (EditText) findViewById(R.id.CarMake_edit);
        model = (EditText) findViewById(R.id.CarModel_edit);
        fault = (EditText) findViewById(R.id.Desc_edit);
        location = (EditText) findViewById(R.id.Location_edit);
        submit = (Button) findViewById(R.id.request_button);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(make.getText()!=null && model.getText()!=null && fault.getText()!=null && location.getText()!=null){
                    RequestQueue queue = Volley.newRequestQueue(RequestActivity.this);


                    JSONObject postData = new JSONObject();
                    try{
                        postData.put("User","");
                        postData.put("CarMake",make.getText().toString());
                        postData.put("CarModel",model.getText().toString());
                        postData.put("Description",fault.getText().toString());
                        postData.put("Description",location.getText().toString());
                    }
                    catch (JSONException ex){

                    }
                    String url = "";
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,postData,
                            response -> {
                                try{
                                    String message = response.getString("message");
                                }catch (JSONException ex){

                                }
                            },error -> {
                        Toast.makeText(RequestActivity.this,"Failed to submit request.",Toast.LENGTH_SHORT).show();
                    });
                    queue.add(request);
                }
                else{
                    Toast.makeText(RequestActivity.this,"Input field not complete.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}