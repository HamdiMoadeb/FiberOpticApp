package com.outsider.networkapplication;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String route = "";
    ProgressDialog progressDialog;
    ArrayList<LatLng> latlngsBoussalem;
    ArrayList<LatLng> latlngsBeja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        route = getIntent().getStringExtra("route");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, ChartsActivity.class);
                intent.putExtra("route", route);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        latlngsBeja = new ArrayList<>();
        latlngsBoussalem = new ArrayList<>();

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        Ion.with(MapsActivity.this)
                .load("https://fibre-backend.herokuapp.com/MapsActivity")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressDialog.dismiss();
                        if(e == null){
                            if(route.equals("boussalem")){
                                JsonArray jsonBoussalem = result.get("LatLngBoussalem").getAsJsonArray();
                                for (int i = 0; i < jsonBoussalem.size(); i++){
                                    JsonObject jsonLatlng = jsonBoussalem.get(i).getAsJsonObject();
                                    latlngsBoussalem.add(new LatLng(jsonLatlng.get("lat").getAsDouble(), jsonLatlng.get("lng").getAsDouble()));
                                }
                                LatLng mm = new LatLng(36.690865, 9.237541);

                                mMap.addMarker(new MarkerOptions().position(latlngsBoussalem.get(0)).title("Room 1").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBoussalem.get(6)).title("Room 2").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBoussalem.get(7)).title("Room 3").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBoussalem.get(10)).title("Room 4").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBoussalem.get(13)).title("Room 5").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                                // Add polylines to the map.
                                // Polylines are useful to show a route or some other connection between points.
                                Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                                        .clickable(true)
                                        .add(latlngsBoussalem.get(0),
                                                latlngsBoussalem.get(1),
                                                latlngsBoussalem.get(2),
                                                latlngsBoussalem.get(3),
                                                latlngsBoussalem.get(4),
                                                latlngsBoussalem.get(5),
                                                latlngsBoussalem.get(6),
                                                latlngsBoussalem.get(7),
                                                latlngsBoussalem.get(8),
                                                latlngsBoussalem.get(9),
                                                latlngsBoussalem.get(10),
                                                latlngsBoussalem.get(11),
                                                latlngsBoussalem.get(12),
                                                latlngsBoussalem.get(13)));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mm, 10));

                            }else{

                                JsonArray jsonBeja = result.get("LatLngBeja").getAsJsonArray();
                                for (int i = 0; i < jsonBeja.size(); i++){
                                    JsonObject jsonLatlng = jsonBeja.get(i).getAsJsonObject();
                                    latlngsBeja.add(new LatLng(jsonLatlng.get("lat").getAsDouble(), jsonLatlng.get("lng").getAsDouble()));
                                }
                                LatLng gg = new LatLng(36.740269, 9.187097);

                                mMap.addMarker(new MarkerOptions().position(latlngsBeja.get(0)).title("Room 1").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBeja.get(2)).title("Room 2").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBeja.get(3)).title("Failure"));
                                mMap.addMarker(new MarkerOptions().position(gg).title("Room 4").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                mMap.addMarker(new MarkerOptions().position(latlngsBeja.get(5)).title("Room 5").icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                                // Add polylines to the map.
                                // Polylines are useful to show a route or some other connection between points.
                                Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                                        .clickable(true)
                                        .add(latlngsBeja.get(0),
                                                latlngsBeja.get(1),
                                                latlngsBeja.get(2),
                                                latlngsBeja.get(3),
                                                latlngsBeja.get(4),
                                                latlngsBeja.get(5)));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngsBeja.get(3), 15));
                            }
                        }else{
                            Toast.makeText(MapsActivity.this, "Sorry something went wrong !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        //draw route :'( (billing account)
        /*mMap.addMarker(new MarkerOptions().position(ll6).title("Marker 6").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.addMarker(new MarkerOptions().position(ll7).title("Marker 7").icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        String url = getUrl(ll6, ll7);
        Log.d("onMapClick", url.toString());
        FetchUrl FetchUrl = new FetchUrl();
        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ll6));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));*/
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private String getUrl(LatLng origin, LatLng dest) {


        //  String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters + "&key=" + MY_API_KEY
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters
                + "&key=" + "AIzaSyBvAkgtkT-ZB4o3tlm0z2aWD3z8gRxwBS8";


        return url;
    }
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(strings[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());
                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());
            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
                Log.d("onPostExecute","onPostExecute lineoptions decoded");
            }
            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }


}


