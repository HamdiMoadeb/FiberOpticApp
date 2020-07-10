package com.outsider.networkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {


    Spinner spinAdvice, spinRoutes;

    Button btnmap;
    int routeselected;
    ProgressDialog progressDialog;
    ArrayAdapter aa, r1, r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Main Page");

        spinAdvice = findViewById(R.id.spinDevice);
        spinRoutes = findViewById(R.id.spinRoutes);
        btnmap = findViewById(R.id.mapBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);


        Ion.with(MainActivity.this)
                .load("https://fibre-backend.herokuapp.com/MainActivity")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressDialog.dismiss();
                        if(e == null){
                            String[] devices = new String[2];
                            String[] routes2 = new String[2];

                            JsonArray jsondevices = result.get("devices").getAsJsonArray();
                            devices[0] = jsondevices.get(0).getAsString();
                            devices[1] = jsondevices.get(1).getAsString();

                            JsonArray jsonroutes2 = result.get("routes2").getAsJsonArray();
                            routes2[0] = jsonroutes2.get(0).getAsString();
                            routes2[1] = jsonroutes2.get(1).getAsString();

                            JsonArray jsonroutes1 = result.get("routes1").getAsJsonArray();
                            String[] routes1 = new String[jsonroutes1.size()];
                            for(int i=0; jsonroutes1.size() > i; i++){
                                routes1[i] = jsonroutes1.get(i).getAsString();
                            }

                            aa = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,devices);
                            r1 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,routes1);
                            r2 = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,routes2);

                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            r1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            r2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinAdvice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(position == 0){
                                        spinRoutes.setAdapter(r1);
                                    }else{
                                        spinRoutes.setAdapter(r2);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            spinRoutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    routeselected = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            spinAdvice.setAdapter(aa);
                        }else{
                            Toast.makeText(MainActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });





        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(routeselected == 2){
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("route", "beja");
                    startActivity(intent);
                }else if(routeselected == 3){
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("route", "boussalem");
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "No Data provided!", Toast.LENGTH_LONG).show();
                }
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