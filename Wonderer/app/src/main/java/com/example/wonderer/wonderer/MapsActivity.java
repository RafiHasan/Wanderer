package com.example.wonderer.wonderer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wonderer.wonderer.Socialdir.SocialAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Button b;
    ImageView i;
    public static GoogleMap mMap;
    ImageView imageView;
    Bitmap bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

          b=(Button)findViewById(R.id.button2);
        i=(ImageView)findViewById(R.id.image2);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-134, 151);
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        for(int i = 0; i< SocialAdapter.d.travel.size(); i++)
        {
             sydney = new LatLng(SocialAdapter.d.travel.get(i).lat, SocialAdapter.d.travel.get(i).lan);
            mMap.setMyLocationEnabled(true);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Place"+i));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }

      //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sydney.getLatitude(), sydney.getLongitude()), 14.0f));
      /*  for(int i=0;i<SocialAdapter.d.photo.size();i++)
        {   ImageView I=new ImageView(getApplicationContext());

            sydney = new LatLng(SocialAdapter.d.photo.get(i).lat, SocialAdapter.d.photo.get(i).lan);
            mMap.setMyLocationEnabled(true);
            Bitmap bitmap = ((BitmapDrawable)I.getDrawable()).getBitmap();
            mMap.addMarker(new MarkerOptions().position(sydney).title("Place"+i)).setIcon();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }*/

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                  Location  myloc = location;
                    LatLng l = new LatLng(myloc.getLatitude(), myloc.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(l).title("mylocation"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(l));

            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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




    }


}
