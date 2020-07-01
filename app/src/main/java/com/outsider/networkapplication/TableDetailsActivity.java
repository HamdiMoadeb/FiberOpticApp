package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TableDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_details);


        getSupportActionBar().setTitle("Event 4");
    }
}