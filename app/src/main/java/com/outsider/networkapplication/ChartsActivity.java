package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class ChartsActivity extends AppCompatActivity {

    ListView listView;
    TextView seeall, file, device, module, date;
    String route = "";

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

        ArrayList<Routes> routes = new ArrayList<>();
        List<DataEntry> seriesData = new ArrayList<>();
        if(route.equals("boussalem")){

            file.setText("Fiber Oued zarga vers boussalem2");
            date.setText("08/10/2019 12:33:10");
            device.setText("MTS 6000A V No. 0");
            module.setText("2142 No. 8136 B");

            seriesData.add(new CustomDataEntry("1", 0, 0, 14));
            seriesData.add(new CustomDataEntry("10", 0, 0, -5));
            seriesData.add(new CustomDataEntry("20", 0, 0, -8));
            seriesData.add(new CustomDataEntry("30", 0, 0, -10));
            seriesData.add(new CustomDataEntry("40", 0, 0, -12));
            seriesData.add(new CustomDataEntry("50", 0, 0, -14));

            routes.add(new Routes("1", "- 521.5", "452.36"));
            routes.add(new Routes("2", "- 320.5", "54412.36"));
            routes.add(new Routes("3", "- 201.5", "214.36"));
            routes.add(new Routes("4", "- 874.5", "4110.36"));
            routes.add(new Routes("5", "- 521.5", "54412.36"));
            routes.add(new Routes("6", "- 11.5", "1230.36"));
            routes.add(new Routes("8", "- 22.5", "1220.36"));
            routes.add(new Routes("9", "- 471.5", "1002.36"));
            routes.add(new Routes("10", "- 520.5", "1002.36"));
            routes.add(new Routes("11", "- 230.5", "54412.36"));
            routes.add(new Routes("12", "- 38.5", "1002.36"));
        }else {
            file.setText("Fiber beja vers garde nationale");
            date.setText("08/10/2019 13:21:40");
            device.setText("MTS 6000A V No. 0");
            module.setText("2142 No. 8136 B");

            seriesData.add(new CustomDataEntry("1", 0, 0, 10));
            seriesData.add(new CustomDataEntry("2", 0, 0, -1));
            seriesData.add(new CustomDataEntry("3", 0, 0, 0));
            seriesData.add(new CustomDataEntry("4", 0, 0, 0));
            seriesData.add(new CustomDataEntry("5", 0, 0, 0));

            routes.add(new Routes("1", "- 44.55", "985.35"));
        }

        AdapterListData adapterListData = new AdapterListData(this, R.layout.item_data, routes);
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
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

}