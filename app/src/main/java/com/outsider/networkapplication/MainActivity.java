package com.outsider.networkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Spinner spinAdvice, spinRoutes;
    String[] devices = { "000000", "111111", "222222", "333333", "444444"};
    String[] routes = { "Route 1", "Route 2", "Route 3", "Route 4", "Route 5"};
    Button btnmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Main Page");

        spinAdvice = findViewById(R.id.spinDevice);
        spinRoutes = findViewById(R.id.spinRoutes);
        btnmap = findViewById(R.id.mapBtn);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,devices);
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item,routes);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinAdvice.setAdapter(aa);
        spinRoutes.setAdapter(bb);

        spinAdvice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),devices[position] , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.aboutid){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        return true;
    }
}