package com.example.autosmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    EditText name,emal,password,cpassword,phone;
    Button register;
    SharedPreferences UserEmail;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.r_name_edit);
        emal = findViewById(R.id.r_email_edit);
        phone = findViewById(R.id.r_phone_edit);
        password = findViewById(R.id.r_pass_edit);
        cpassword = findViewById(R.id.r_con_edit);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();


        register = (Button) findViewById(R.id.Register_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = emal.getText().toString();
                String pwd = password.getText().toString();
                String phoneN = phone.getText().toString();
                String Uname = name.getText().toString();
                    if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(phoneN) && TextUtils.isEmpty(Uname)){


                        Toast.makeText(RegisterActivity.this,"Input field not complete.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // on below line we are checking if the task is success or not.
                                if (task.isSuccessful()) {


                                    // in on success method we are hiding our progress bar and opening a login activity.

                                    Map<String, Object> users = new HashMap<>();
                                    users.put("FullName", Uname);
                                    users.put("Email", userName);
                                    users.put("Phone", phoneN);

                                    db.collection("users")
                                            .add(users)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });

                                    Toast.makeText(RegisterActivity.this, "User Registered..", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {

                                    // in else condition we are displaying a failure toast message.
                                    Toast.makeText(RegisterActivity.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                    }
                }

        });

    }
}