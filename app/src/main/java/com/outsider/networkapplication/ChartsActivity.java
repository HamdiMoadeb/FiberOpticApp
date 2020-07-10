package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class ChartsActivity extends AppCompatActivity {

    ListView listView;
    TextView seeall, file, device, module, date;
    String route = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        route = getIntent().getStringExtra("route");

        getSupportActionBar().setTitle("Chart");

        listView = findViewById(R.id.listData);
        seeall = findViewById(R.id.seealltv);
        file = findViewById(R.id.filetv);
        device = findViewById(R.id.devicetv);
        module = findViewById(R.id.moduletv);
        date = findViewById(R.id.datetv);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);


        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartsActivity.this, InformationDetailsActivity.class);
                intent.putExtra("route", route);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChartsActivity.this, TableDetailsActivity.class);
                startActivity(intent);
            }
        });


        Ion.with(ChartsActivity.this)
                .load("https://fibre-backend.herokuapp.com/ChartsActivity")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        ArrayList<Routes> routes = new ArrayList<>();
                        List<DataEntry> seriesData = new ArrayList<>();

                        if(e == null){
                            if(route.equals("boussalem")){
                                JsonArray boussalemSeriesData = result.get("boussalemSeriesData").getAsJsonArray();
                                for (int i = 0; i < boussalemSeriesData.size(); i++){
                                    JsonObject jsonSeries = boussalemSeriesData.get(i).getAsJsonObject();
                                    seriesData.add(new CustomDataEntry(jsonSeries.get("x").getAsString(),
                                            jsonSeries.get("value").getAsBigInteger(),
                                            jsonSeries.get("value2").getAsBigInteger(),
                                            jsonSeries.get("value3").getAsBigInteger()));
                                }
                                JsonArray boussalemRoutes = result.get("boussalemRoutes").getAsJsonArray();
                                for (int i = 0; i < boussalemRoutes.size(); i++){
                                    JsonObject jsonRoutes = boussalemRoutes.get(i).getAsJsonObject();
                                    routes.add(new Routes(jsonRoutes.get("event").getAsString(),
                                            jsonRoutes.get("loss").getAsString(),
                                            jsonRoutes.get("distance").getAsString()));
                                }

                                file.setText(R.string.fibera);
                                date.setText(R.string.date);
                                device.setText(R.string.device1);
                                module.setText(R.string.module1);

                            }else{

                                JsonArray bejaSeriesData = result.get("bejaSeriesData").getAsJsonArray();
                                for (int i = 0; i < bejaSeriesData.size(); i++){
                                    JsonObject jsonSeries = bejaSeriesData.get(i).getAsJsonObject();
                                    seriesData.add(new CustomDataEntry(jsonSeries.get("x").getAsString(),
                                            0,
                                            0,
                                            jsonSeries.get("value").getAsBigInteger()));
                                }
                                JsonArray bejaRoutes = result.get("bejaRoutes").getAsJsonArray();
                                for (int i = 0; i < bejaRoutes.size(); i++){
                                    JsonObject jsonRoutes = bejaRoutes.get(i).getAsJsonObject();
                                    routes.add(new Routes(jsonRoutes.get("event").getAsString(),
                                            jsonRoutes.get("loss").getAsString(),
                                            jsonRoutes.get("distance").getAsString()));
                                }

                                file.setText(R.string.fiberb);
                                date.setText(R.string.dateb);
                                device.setText(R.string.deviceb);
                                module.setText(R.string.moduleb);

                            }
                            progressDialog.dismiss();

                            AdapterListData adapterListData = new AdapterListData(ChartsActivity.this, R.layout.item_data, routes);
                            listView.setAdapter(adapterListData);

                            AnyChartView anyChartView = findViewById(R.id.any_chart_view);
                            anyChartView.setProgressBar(findViewById(R.id.progrss));

                            Cartesian cartesian = AnyChart.line();
                            cartesian.animation(true);
                            cartesian.padding(10d, 20d, 5d, 20d);
                            cartesian.crosshair().enabled(true);
                            cartesian.crosshair()
                                    .yLabel(true)
                                    // TODO ystroke
                                    .yStroke((Stroke) null, null, null, (String) null, (String) null);

                            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                            cartesian.title("OTDR Trace");
                            cartesian.yAxis(0).title("dB");
                            cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                            Set set = Set.instantiate();
                            set.data(seriesData);
                            Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

                            Line series3 = cartesian.line(series3Mapping);
                            series3.name("Loss");
                            series3.hovered().markers().enabled(true);
                            series3.hovered().markers()
                                    .type(MarkerType.CIRCLE)
                                    .size(4d);
                            series3.tooltip()
                                    .position("right")
                                    .anchor(Anchor.LEFT_CENTER)
                                    .offsetX(5d)
                                    .offsetY(5d);

                            cartesian.legend().enabled(true);
                            cartesian.legend().fontSize(13d);
                            cartesian.legend().padding(0d, 0d, 10d, 0d);

                            anyChartView.setChart(cartesian);
                        }else{
                            Toast.makeText(ChartsActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

}