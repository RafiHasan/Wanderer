package com.example.wonderer.wonderer.plandir;

import android.content.Context;
import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wonderer.wonderer.Commentdir.CommentActivity;
import com.example.wonderer.wonderer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class DetailLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    int ratechange=0;
    float prevrating;
     public TextView review;
   public static Place thisplace=new Place();

    List<Fragmentdetail> imagelist=new ArrayList<Fragmentdetail>();

    @Override
    public void onBackPressed()
    {
        if(TravelPlaceList.ai.size()>0)
        {

            TravelPlaceList.indicator=2;
            Main2Activity.arraycount++;
            Main2Activity.arraycount%=2;

            if(Main2Activity.arraycount==1)
                TravelPlaceList.ai.remove(thisplace);
            if(Main2Activity.arraycount==0)
                TravelPlaceList.placelist2.remove(thisplace);

        }

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaillocation_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Switch swi=(Switch)findViewById(R.id.switch2);
        swi.setChecked(false);
        review=(TextView)findViewById(R.id.textView3);

        review.setText(thisplace.comment.size()+" reviews");

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.comref= TravelPlaceList.place.child(String.valueOf(thisplace.number)).child("comment");
                Intent i=new Intent(getApplicationContext(),CommentActivity.class);
                startActivity(i);
            }
        });

     /*   for(int i=0;i<TravelPlaceList.plan.Locationlist.size();i++)
        {
            if(thisplace.address.equals(TravelPlaceList.plan.Locationlist.get(i).address))
            {
                swi.setChecked(true);
                break;
            }
        }*/
        swi.setChecked(false);

        if(TravelPlaceList.plan.Locationlist.contains(thisplace))
        {
            swi.setChecked(true);
        }



        swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                    if(!TravelPlaceList.plan.Locationlist.contains(thisplace))
                    {
                        TravelPlaceList.plan.Locationlist.add(thisplace);
                    }


                    TravelPlaceList.indicator=2;
                    TravelPlaceList.ai.remove(thisplace);
                    TravelPlaceList.placelist2.remove(thisplace);

                }
                else
                {   TravelPlaceList.ai.add(thisplace);
                    TravelPlaceList.placelist2.add(thisplace);
                    TravelPlaceList.plan.Locationlist.remove(thisplace);

                    TravelPlaceList.indicator=2;

                }
                TravelPlaceList.mAdapter.notifyDataSetChanged();
            }
        });


        for(int i=0;i<thisplace.url.size();i++)
        {
            Fragmentdetail f=new Fragmentdetail();
            f.Fragmentload(thisplace.url.get(i),this);
            imagelist.add(f);
        }


    //    RatingBar r=(RatingBar) findViewById(R.id.ratingBar);
     //   r.setRating(dloc.rating);
      //  final RatingBar rater=(RatingBar)findViewById(R.id.ratingBar2);
     /*   rater.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratechange==0)
                {dloc.giverating(rater.getRating());
                    prevrating=rater.getRating();
                    ratechange=1;}

            }
        });*/

      //  TextView reviews=(TextView)findViewById(R.id.textView3);
      //  reviews.setText(String.valueOf(dloc.rater)+"reviews");

        TextView description=(TextView)findViewById(R.id.textView2);
        description.setText("Short Description\n"+thisplace.Description);

       // final Switch select=(Switch) findViewById(R.id.switch2);

       // select.setChecked(dloc.selected);

    /*    select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(select.isChecked())
                    dloc.selected=true;
                else
                    dloc.selected=false;
            }
        });*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(thisplace.Name);

       // setSupportActionBar(toolbar);
      //  ActionBar actionBar = getSupportActionBar();
      //  actionBar.setDisplayHomeAsUpEnabled(true);

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


    
   /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setLocationSource (null);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(thisplace.location.lat, thisplace.location.lan);
        mMap.addMarker(new MarkerOptions().position(sydney).title(thisplace.Name));
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
            return thisplace.Name;
        }
    }

}
