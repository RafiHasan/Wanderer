package com.example.wonderer.wonderer.plandir;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wonderer.wonderer.Backgroundlocation;
import com.example.wonderer.wonderer.Completetourdir.DataParser;
import com.example.wonderer.wonderer.Homedir.Detailhome;
import com.example.wonderer.wonderer.Homedir.Home;
import com.example.wonderer.wonderer.Homedir.Homelist;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Socialdir.SocialActivity;
import com.example.wonderer.wonderer.Socialdir.dummytour;
import com.example.wonderer.wonderer.loginregister.Login;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;*/

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback ,GoogleMap.OnMarkerClickListener {
  //  private BoomMenuButton bmb;
    LatLng start;
    LatLng end;
    int once=0;
    public Location myloc;
    public boolean isset=false;
    public static List <Location> travel=new ArrayList<Location>();
    List <Location> photo=new ArrayList<Location>();
    List<Bitmap> bit=new ArrayList<Bitmap>();
    List<String> url=new ArrayList<String>();
    public  static Context c;

    public List<Marker> mark=new ArrayList<Marker>();

    public static Plantour showplantour=new Plantour();

    FloatingActionButton fab1;
    FloatingActionButton fab2;
int check=0;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private Custom2nd mAdapter;
    private GoogleMap mMap;


    public static boolean make=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
c=this;

        fab1=(FloatingActionButton)findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);

        fab2.setVisibility(View.GONE);



        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(make==false)
                {

                    String key= Login.plantour.push().getKey();
                    String key2= Login.makeprofile.child(Login.userid).child("Plantour").push().getKey();
                    Login.plantour.child(key).setValue(showplantour);
                    Login.makeprofile.child(Login.userid).child("Plantour").child(key2).setValue(key);

                    TravelPlaceList.plantour=new ArrayList<Place>();
                    Homelist.hometour=new ArrayList<Home>();

                    finish();
                }
                else {

                    if (!isset) {

                        isset = true;
                        fab2.setVisibility(View.VISIBLE);
                        try {

                            List<Location> l = Backgroundlocation.l;
                            Toast.makeText(getApplicationContext(), String.valueOf(l.size()), Toast.LENGTH_LONG).show();
                            Intent b = new Intent(getApplicationContext(), Backgroundlocation.class);
                            startService(b);
                        } catch (Exception e) {
                            Intent b = new Intent(getApplicationContext(), Backgroundlocation.class);
                            startService(b);
                        }
                    } else {
                        fab2.setVisibility(View.GONE);
                        final String id = Login.maketour.push().getKey();

                        isset = false;

                        for (int i = 0; i < bit.size(); i++) {
                            Bitmap bitmap = bit.get(i);
                            // i.setImageBitmap(bitmap);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();
                            StorageReference mountainImagesRef = storageRef.child("rafi/" + id + "/" + String.valueOf(i) + ".jpg");
                            UploadTask uploadTask = mountainImagesRef.putBytes(data);

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    String down = taskSnapshot.getDownloadUrl().toString();
                                    url.add(down);
                                    check++;

                                    if (check == bit.size()) {


                                        dummytour d = new dummytour(travel, photo, url);
                                        d.userid = Login.userid;
                                        Login.maketour.child(id).setValue(d);

                                        Login.makeprofile.child(Login.userid).child("Completed").child(String.valueOf(Login.userprofile.Completed.size())).setValue(id);

                                       finish();

                                    }

                                }
                            });
                        }
                    }
                }
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,0);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);//

        mRecyclerView = (RecyclerView) findViewById(R.id.list);//
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);//
        mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
        mAdapter = new Custom2nd(this,showplantour.Locationlist);//
        mRecyclerView.setAdapter(mAdapter);//



    }

    private String getUrl() {

        String waypoint="&"+"waypoints=optimize:true";
        for(int i=1;i<showplantour.Locationlist.size()-1;i++)
        {
            waypoint+="|"+showplantour.Locationlist.get(i).location.lat+","+showplantour.Locationlist.get(i).location.lan;
        }


        waypoint+="|"+String.valueOf(myloc.getLatitude())+","+String.valueOf(myloc.getLongitude());
        // Origin of route

        LatLng org=new LatLng(showplantour.Locationlist.get(0).location.lat,showplantour.Locationlist.get(0).location.lan);

        String str_origin = "origin=" + org.latitude + "," + org.longitude;
        org=new LatLng(showplantour.Locationlist.get(1).location.lat,showplantour.Locationlist.get(1).location.lan);
        // Destination of route
        String str_dest = "destination=" + showplantour.Locationlist.get(showplantour.Locationlist.size()-1).location.lat + "," + showplantour.Locationlist.get(showplantour.Locationlist.size()-1).location.lan;


        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest +waypoint+ "&" + sensor;
        Toast.makeText(c, parameters, Toast.LENGTH_SHORT).show();

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;Toast.makeText(this, "maptest", Toast.LENGTH_SHORT).show();
        mMap.setLocationSource (null);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(showplantour.Locationlist.get(0).location.lat, showplantour.Locationlist.get(0).location.lan);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        for(int i=0;i<showplantour.Locationlist.size();i++) {
             sydney = new LatLng(showplantour.Locationlist.get(i).location.lat, showplantour.Locationlist.get(i).location.lan);
            mMap.addMarker(new MarkerOptions().position(sydney).title(showplantour.Locationlist.get(i).Name));
        }
        mark=new ArrayList<Marker>();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.home_icon);

        for(int i = 0; i< SocialActivity.homelist.size(); i++)
        {
             sydney = new LatLng(SocialActivity.homelist.get(i).location.lat, SocialActivity.homelist.get(i).location.lan);
          Marker m= mMap.addMarker(new MarkerOptions().position(sydney).title(SocialActivity.homelist.get(i).address).icon(icon));
            m.setTag(i);
            mark.add(m);
        }


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(once==0) {
                    myloc = location;
                    LatLng l = new LatLng(myloc.getLatitude(), myloc.getLongitude());
                    //  mMap.addMarker(new MarkerOptions().position(l).title("mylocation"));

                    once=1;
                    String url = getUrl();
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                }
            }
        });



        //move map camera

        if(showplantour.Locationlist.size()>0)
        {LatLng start=new LatLng(showplantour.Locationlist.get(0).location.lat,showplantour.Locationlist.get(0).location.lan);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));}

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }

        mMap.setMyLocationEnabled(true);
       /* mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Already two locations
                if (MarkerPoints.size() > 1) {
                   // MarkerPoints.clear();
                  //  mMap.clear();
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);


           //     if (MarkerPoints.size() == 1) {
            //        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
             //   } else if (MarkerPoints.size() == 2) {
             //       options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
             //   }


                // Add new marker to the Google Map Android API V2
            //    mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }

            }
        });*/

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

          int i=(int)(marker.getTag());
Toast.makeText(getApplicationContext(),"tosted",Toast.LENGTH_LONG).show();
        Detailhome.thishome= Homelist.homelist.get(i);
        Intent intent4 = new Intent(getApplicationContext(), Detailhome.class);
        startActivity(intent4);

        return true;
    }

    class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
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
    }

    class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
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
                lineOptions.color(Color.BLUE);

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


    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);

        try{
            Bitmap img=(Bitmap)data.getExtras().get("data");
            Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_LONG).show();
            bit.add(img);
            photo.add(Backgroundlocation.l.get(Backgroundlocation.l.size()-1));


        }
        catch(Exception e)
        {}


    }

}
