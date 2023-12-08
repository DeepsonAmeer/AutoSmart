package com.example.autosmart;

import androidx.appcompat.app.AppCompatActivity;

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

public class TowingActivity extends AppCompatActivity {

    EditText make,model,des,cont,loc,dest;
    Button submit;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_towing);

        make = (EditText) findViewById(R.id.T_CarMake_edit);
        model = (EditText) findViewById(R.id.T_CarModel_edit);
        des = (EditText) findViewById(R.id.T_Desc_edit);
        cont = (EditText) findViewById(R.id.T_Contact_edit);
        loc = (EditText) findViewById(R.id.T_Location_edit);
        dest = (EditText) findViewById(R.id.T_Destination_edit);
        submit = (Button) findViewById(R.id.towing_button);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(make.getText().toString()) && !TextUtils.isEmpty(model.getText().toString()) && !TextUtils.isEmpty(des.getText().toString()) && !TextUtils.isEmpty(loc.getText().toString()) && !TextUtils.isEmpty(cont.getText().toString()) && !TextUtils.isEmpty(dest.getText().toString())){
                    RequestQueue queue = Volley.newRequestQueue(TowingActivity.this);

                    FirebaseUser user = mAuth.getCurrentUser();
                    String email = user.getEmail();
                    JSONObject postData = new JSONObject();
                    try{

                        postData.put("Make",make.getText().toString());
                        postData.put("Model",model.getText().toString());
                        postData.put("Name",email);
                        postData.put("Description",des.getText().toString());
                        postData.put("Contact",cont.getText().toString());
                        postData.put("Location",loc.getText().toString());
                        postData.put("Destination",dest.getText().toString());

                    }
                    catch (JSONException ex){

                    }
                    String url = getResources().getString(R.string.endpoint)+"/api/Towings";
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,postData,
                            response -> {
                                try{
                                    String message = response.getString("message");
                                    Toast.makeText(TowingActivity.this,"Request submitted successfullly.",Toast.LENGTH_SHORT).show();
                                }catch (JSONException ex){

                                }
                            },error -> {
                        Toast.makeText(TowingActivity.this,"Failed to submit request."+error.getMessage(),Toast.LENGTH_SHORT).show();
                    });
                    queue.add(request);
                }
                else{
                    Toast.makeText(TowingActivity.this,"Input field not complete.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}