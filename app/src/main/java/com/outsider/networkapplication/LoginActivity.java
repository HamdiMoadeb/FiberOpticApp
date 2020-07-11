package com.outsider.networkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView gotoregister, gotforgot;
    EditText emailET, passwordET;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        gotoregister = findViewById(R.id.goToRegister);
        gotforgot = findViewById(R.id.gotToForgot);
        emailET = findViewById(R.id.emaillogin);
        passwordET = findViewById(R.id.passlogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("TAG", token);
                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });*/

        gotforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                String email = emailET.getText().toString();
                String pass = passwordET.getText().toString();

                JsonObject json = new JsonObject();
                json.addProperty("email", email);
                json.addProperty("password", pass);

                Ion.with(LoginActivity.this)
                        .load("https://fibre-backend.herokuapp.com/user/login")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                progressDialog.dismiss();

                                if(e == null){
                                    if(result.get("action") != null){
                                        if(result.get("action").getAsBoolean()){
                                            finish();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Incorrect email or password !", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Incorrect email or password !", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(LoginActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}