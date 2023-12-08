package com.example.autosmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView gbtn;
    TextView rbtn;
    Button lbtn;
    EditText email,password;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    SharedPreferences sharedpreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        sharedpreferences = getSharedPreferences("myref", Context.MODE_PRIVATE);
        email = (EditText) findViewById(R.id.login_email_edit);
        password = (EditText) findViewById(R.id.login_pass_edit);
        rbtn = (TextView) findViewById(R.id.regi_btn);
        lbtn = (Button) findViewById(R.id.Register_btn);
        //UserEmail = getSharedPreferences("email", Context.MODE_PRIVATE);


        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });

        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = email.getText().toString();
                String pwd = password.getText().toString();
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                    return;
                }
                // on below line we are calling a sign in method and passing email and password to it.
                mAuth.signInWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // on below line we are checking if the task is success or not.
                        if (task.isSuccessful()) {
                            // on below line we are hiding our progress bar.
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            db.collection("users")
                                    .whereEqualTo("Email",userName)
                                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                                                    if(queryDocumentSnapshot.getData()!=null){
                                                        editor.putString("FullName",queryDocumentSnapshot.get("FullName").toString());
                                                        editor.apply();
                                                    }

                                                }
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                            Toast.makeText(LoginActivity.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                            // on below line we are o"pening our mainactivity.
                            Intent i = new Intent(LoginActivity.this, Home.class);
                            startActivity(i);
                            // requireActivity().finish();
                        } else {
                            // hiding our progress bar and displaying a toast message.
                            Toast.makeText(LoginActivity.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    public void onStart() {
        super.onStart();
        // in on start method checking if
        // the user is already sign in.
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // if the user is not null then we are
            // opening a main activity on below line.
            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
            //requireActivity().finish();
        }

    }


}