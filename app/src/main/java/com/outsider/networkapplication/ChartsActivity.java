package com.outsider.networkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        getSupportActionBar().setTitle("Chart");

        listView = findViewById(R.id.listData);

        ArrayList<Routes> routes = new ArrayList<>();
        routes.add(new Routes("1", "- 521.5", "452.36"));
        routes.add(new Routes("2", "- 320.5", "54412.36"));
        routes.add(new Routes("3", "- 201.5", "214.36"));
        routes.add(new Routes("4", "- 874.5", "4110.36"));
        routes.add(new Routes("5", "- 521.5", "54412.36"));
        routes.add(new Routes("6", "- 11.5", "1230.36"));
        routes.add(new Routes("7", "- 230.5", "1002.36"));

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

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("1", 0, 0, 14));
        seriesData.add(new CustomDataEntry("10", 0, 0, -5));
        seriesData.add(new CustomDataEntry("20", 0, 0, -8));
        seriesData.add(new CustomDataEntry("30", 0, 0, -10));
        seriesData.add(new CustomDataEntry("40", 0, 0, -12));
        seriesData.add(new CustomDataEntry("50", 0, 0, -14));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

//        Line series1 = cartesian.line(series1Mapping);
//        series1.name("Brandy");
//        series1.hovered().markers().enabled(true);
//        series1.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series1.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);

//        Line series2 = cartesian.line(series2Mapping);
//        series2.name("Whiskey");
//        series2.hovered().markers().enabled(true);
//        series2.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series2.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);

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