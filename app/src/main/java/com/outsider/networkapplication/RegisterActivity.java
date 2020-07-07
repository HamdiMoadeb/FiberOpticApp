package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn;
    TextView gotologin;
    EditText usernameET, emailEt, passET, confirmPassET;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.registerBtn);
        gotologin = findViewById(R.id.gotologin);

        usernameET = findViewById(R.id.usernameRegister);
        emailEt = findViewById(R.id.emailRegister);
        passET = findViewById(R.id.passRegister);
        confirmPassET = findViewById(R.id.confirmPassRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                String email = emailEt.getText().toString();
                String pass = passET.getText().toString();
                String confirmpass = confirmPassET.getText().toString();
                String username = usernameET.getText().toString();

                if(!confirmpass.equals(pass)){
                    progressDialog.dismiss();
                    confirmPassET.requestFocus();
                    passET.setError("Passwords need to match");
                    confirmPassET.setError("Passwords need to match");
                }else {
                    JsonObject json = new JsonObject();
                    json.addProperty("email", email);
                    json.addProperty("username", username);
                    json.addProperty("password", pass);

                    Ion.with(RegisterActivity.this)
                            .load("https://fibre-backend.herokuapp.com/user/register")
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    progressDialog.dismiss();
                                    if(e == null){
                                        if(result.get("action") != null){
                                            if(result.get("action").getAsBoolean()){
                                                Toast.makeText(RegisterActivity.this, "Welcome !", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(RegisterActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(RegisterActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}