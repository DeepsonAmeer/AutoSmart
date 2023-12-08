package com.example.autosmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestActivity extends AppCompatActivity {

    EditText make,model,fault,location;
    Button submit;
    SharedPreferences sharedpreferences;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        mAuth = FirebaseAuth.getInstance();

        make = (EditText) findViewById(R.id.CarMake_edit);
        model = (EditText) findViewById(R.id.CarModel_edit);
        fault = (EditText) findViewById(R.id.Desc_edit);
        location = (EditText) findViewById(R.id.Location_edit);
        submit = (Button) findViewById(R.id.request_button);

        sharedpreferences = getSharedPreferences("email", Context.MODE_PRIVATE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(make.getText().toString()) && !TextUtils.isEmpty(model.getText().toString()) && !TextUtils.isEmpty(fault.getText().toString()) && !TextUtils.isEmpty(location.getText().toString())){
                    RequestQueue queue = Volley.newRequestQueue(RequestActivity.this);
                    FirebaseUser user = mAuth.getCurrentUser();
                    String email = user.getEmail();

                    JSONObject postData = new JSONObject();
                    try{

                        postData.put("Make",make.getText().toString());
                        postData.put("Model",model.getText().toString());
                        postData.put("User",email);
                        postData.put("Description",fault.getText().toString());
                        postData.put("Location",location.getText().toString());
                        postData.put("Engineer","");
                        postData.put("Status","");

                    }
                    catch (JSONException ex){

                    }
                    String url = getResources().getString(R.string.endpoint)+"/api/ServiceRequests";
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,postData,
                            response -> {
                                try{
                                    String message = response.getString("message");
                                    Toast.makeText(RequestActivity.this,"Request submitted successfully.",Toast.LENGTH_SHORT).show();
                                }catch (JSONException ex){

                                }
                            },error -> {
                        Toast.makeText(RequestActivity.this,"Failed to submit request."+error.getMessage(),Toast.LENGTH_SHORT).show();
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