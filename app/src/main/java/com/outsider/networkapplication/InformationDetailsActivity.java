package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InformationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);

        getSupportActionBar().setTitle("Details");
    }
}