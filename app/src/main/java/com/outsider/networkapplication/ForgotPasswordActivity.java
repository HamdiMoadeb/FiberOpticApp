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

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView backtologin;
    Button btnforgot;
    EditText emailET;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backtologin = findViewById(R.id.bachtologin);
        emailET = findViewById(R.id.emailforgot);
        btnforgot = findViewById(R.id.btnforgotf);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                String email = emailET.getText().toString();

                JsonObject json = new JsonObject();
                json.addProperty("email", email);

                Ion.with(ForgotPasswordActivity.this)
                        .load("https://fibre-backend.herokuapp.com/user/forgotPassword")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                progressDialog.dismiss();
                                if(e == null){
                                    if(result.get("action") != null){
                                        if(result.get("action").getAsBoolean()){
                                            Toast.makeText(ForgotPasswordActivity.this, "Check your email !", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(ForgotPasswordActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(ForgotPasswordActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(ForgotPasswordActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}