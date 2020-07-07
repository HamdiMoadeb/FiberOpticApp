package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InformationDetailsActivity extends AppCompatActivity {

    TextView loca, locb, cablea, cableb, fibera, fiberb, file;
    String route = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);

        route = getIntent().getStringExtra("route");

        getSupportActionBar().setTitle("Details");

        loca = findViewById(R.id.locationa);
        locb = findViewById(R.id.locationb);

        cablea = findViewById(R.id.cablea);
        cableb = findViewById(R.id.cableb);

        fibera = findViewById(R.id.fibera);
        fiberb = findViewById(R.id.fiberb);
        file = findViewById(R.id.filedetail);
        if(!route.equals("boussalem")){
            loca.setText("Beja");
            locb.setText("Garde nationale");
            cablea.setText("Cable 12 Fo");
            cableb.setText("Cable 12 Fo");
            fiberb.setText("Fiber 41");
            fibera.setText("Fiber 41");
            file.setText("Fiber Beja vers garde nationale");
        }
    }
}