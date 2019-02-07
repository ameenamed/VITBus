package com.example.android.vitbus1;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import java.io.*;

import android.graphics.Color;
import android.graphics.Path;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.location.LocationServices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.PipedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap, cMap;
    private static final int LOCATION_REQUEST=500;
    private LocationManager locationManager;
    private LocationListener listener;
    private Button b;
    private ListView mValueView;
    private Firebase mRef;
    private ArrayList<String> mkeys = new ArrayList<>();
    private ArrayList<String> lat = new ArrayList<>();

    private ArrayList<LatLng> listpoints=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        Firebase.setAndroidContext(this);
        mRef=new Firebase("https://vitbus1.firebaseio.com/");
        mValueView=(ListView) findViewById(R.id.listview);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lat);
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 01, 0, listener);
        mValueView.setAdapter(arrayAdapter);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                lat.add(value);
                String key=dataSnapshot.getKey();
                mkeys.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String value=dataSnapshot.getValue(String.class);
                String key=dataSnapshot.getKey();
                int index=mkeys.indexOf(key);
                lat.set(index,value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        b = (Button) findViewById(R.id.button);
        //t = (TextView)findViewById(R.id.textView);
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(listpoints.size()==1)
                {
                    listpoints.clear();
                    mMap.clear();
                }
                listpoints.add(latLng);
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(latLng);

                if(listpoints.size()==1)
                {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
                mMap.addMarker(markerOptions);
                /*else
                {
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                mMap.addMarker(markerOptions);

                if(listpoints.size()==2)
                {
                    String url=getRequestUrl(listpoints.get(0),listpoints.get(1));
                    TaskRequestDirection taskRequestDirection=new TaskRequestDirection();
                    taskRequestDirection.execute(url);
                }*/
            }
        });














        //seattle coordinates
        LatLng seattle = new LatLng(12.840232, 80.152984);
        //mMap.addMarker(new MarkerOptions().position(seattle).title("VIT Chennai"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 01, 0, listener);
        //t.append("\n " + 12.840232 + " " + 80.152984);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Firebase mRefChild=mRef.child("Latitude");
                mRefChild.setValue(location.getLatitude());
                Firebase mRefChild1=mRef.child("Longitude");
                mRefChild1.setValue(location.getLongitude());
                //t.append("\n " + location.getLatitude() + " " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        // init();
        configure_button();

    }

    private String getRequestUrl(LatLng latLng, LatLng latLng1) {

        String str_org="origin ="+latLng.latitude+ " , "+latLng.longitude;
        String str_dest="destinaition = "+latLng1.latitude + " , "+latLng1.longitude;
        String sensor="sensor-false";
        String mode="mode=driving";
        String param=str_org + "&"+str_dest+"&"+sensor+"&"+mode;
        String output="json";
        String url="https://maps.googleapis.com/maps/api/directions/"+output+"?"+param;
        return url;

    }
    private String requestDirection(String reqUrl) throws IOException {
        String responseString="";
        InputStream inputStream=null;
        HttpURLConnection httpURLConnection=null;
        try
        {
            URL url=new URL(reqUrl);
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer=new StringBuffer();
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(inputStream!=null)
            {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                //t.append("Not Found");
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 01, 0, listener);
        mMap.setMyLocationEnabled(true);
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1,listener);
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 01, 0, listener);

        });}*/
    }
    public class TaskRequestDirection extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = " ";
            try {
                responseString = requestDirection(strings[0]);

            }catch (IOException e){
                e.printStackTrace();

            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TaskParser taskParser=new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String,Void,List<List<HashMap<String,String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject=null;
            List<List<HashMap<String,String>>> routes = null;
            try
            {
                jsonObject=new JSONObject(strings[0]);
                DirectionsJSONParser directionsJSONParser=new DirectionsJSONParser();
                routes = directionsJSONParser.parse(jsonObject);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return routes;

        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {

            ArrayList points=null;
            PolylineOptions polylineOptions=null;

            for(List<HashMap<String,String>> path:lists)
            {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for(HashMap<String,String>point:path){
                    double lat=Double.parseDouble(point.get("lat"));
                    double lon=Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat,lon));

                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }
            if(polylineOptions!=null)
            {
                mMap.addPolyline(polylineOptions);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Direction not found!",Toast.LENGTH_SHORT).show();
            }

        }
    }



}