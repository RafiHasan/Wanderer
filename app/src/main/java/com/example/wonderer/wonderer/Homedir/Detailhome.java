package com.example.wonderer.wonderer.Homedir;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.plandir.Fragmentdetail;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class Detailhome extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    int ratechange=0;
    float prevrating;

    public static Home thishome=new Home();

    List<Fragmentdetail> imagelist=new ArrayList<Fragmentdetail>();
    TextView description,distance,reviu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaillocation_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        for(int i=0;i<thishome.url.size();i++)
        {
            Fragmentdetail f=new Fragmentdetail();
            f.Fragmentload(thishome.url.get(i),this);
            imagelist.add(f);
        }

        description=(TextView)findViewById(R.id.textView2);
        description.setText("Short Description\n"+thishome.Description);
        distance=(TextView)findViewById(R.id.textView);
        distance.setVisibility(View.GONE);
        reviu=(TextView)findViewById(R.id.textView3);
        reviu.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(thishome.address);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // collapsingToolbarLayout.setTitle(getResources().getString(R.string.user_name));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(new CustomAdapter2(getSupportFragmentManager(),getApplicationContext(),imagelist));


        toolbarTextAppernce();
    }



    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setLocationSource (null);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(thishome.location.lat, thishome.location.lan);
        mMap.addMarker(new MarkerOptions().position(sydney).title(thishome.Userid));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    class CustomAdapter2 extends FragmentPagerAdapter {

        private String fragments [] = {"Fragment 1","Fragment 2","Fragment 3"};
        List<Fragmentdetail> f;
        public CustomAdapter2(FragmentManager supportFragmentManager, Context applicationContext, List<Fragmentdetail> a)
        {
            super(supportFragmentManager);
            f=a;
        }

        @Override
        public Fragment getItem(int position) {
            return f.get(position);
        }

        @Override
        public int getCount() {

            return f.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return thishome.Userid;
        }
    }

}
