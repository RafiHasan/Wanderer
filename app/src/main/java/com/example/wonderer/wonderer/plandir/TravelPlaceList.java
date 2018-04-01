package com.example.wonderer.wonderer.plandir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wonderer.wonderer.Homedir.Homelist;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.AIsystem;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.example.wonderer.wonderer.gamenote.Notific;
import com.example.wonderer.wonderer.loginregister.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TravelPlaceList extends Bottombarnav {

    ImageView play,notification;

  public static RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  public static TravelListAdapter mAdapter;
    FloatingActionButton fab;

   public static int indicator=1;

  //private boolean isListView;
 // private Menu menu;

    public static DatabaseReference place;

 public static List<Place> placelist=new ArrayList<Place>();
    public static List<Place> placelist2=new ArrayList<Place>();
    public static List<Place> placelist3=new ArrayList<Place>();

    public static List<Place> ai=new ArrayList<Place>();

   public static Plantour plan=new Plantour();




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

plan=new Plantour();
      fab=(FloatingActionButton)findViewById(R.id.fab);
      place= FirebaseDatabase.getInstance().getReference("Newplace");

      play=findViewById(R.id.play);
      notification=findViewById(R.id.notification);


      notification.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent i=new Intent(getApplicationContext(), Notific.class);
              startActivity(i);
          }
      });

      play.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Login.mAuth.signOut();
              Bottombarnav.x=3;
              Intent i=new Intent(getApplicationContext(),Login.class);
              startActivity(i);

          }
      });


      placelist=new ArrayList<Place>();
      ai=new ArrayList<Place>();
      place.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              placelist=new ArrayList<Place>();
              placelist3=new ArrayList<Place>();
              for (DataSnapshot d:dataSnapshot.getChildren()) {
                  Place p=d.getValue(Place.class);
                  p.number=d.getKey();
                  placelist.add(p);
                  placelist3.add(p);
                  mAdapter.notifyDataSetChanged();
              }


          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
if(TravelPlaceList.plan.Locationlist.size()>1) {
    plan.homelist = Homelist.hometour;
    Main2Activity.make = false;
    AIsystem AI = new AIsystem();
    TravelPlaceList.ai = AI.Closelocation(TravelPlaceList.placelist3, TravelPlaceList.plan.Locationlist);
    placelist2 = AI.Closelocation(TravelPlaceList.placelist3);
    Toast.makeText(getApplicationContext(), String.valueOf(TravelPlaceList.ai.size()), Toast.LENGTH_LONG).show();
    Intent i = new Intent(getApplicationContext(), Main2Activity.class);
    startActivity(i);
}
else
{
    Toast.makeText(getApplicationContext(),"Please select two or more place",Toast.LENGTH_LONG).show();
}
          }
      });



    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);//

    mRecyclerView = (RecyclerView) findViewById(R.id.list);//
    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);//
    mRecyclerView.setItemViewCacheSize(20);
    mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
    mAdapter = new TravelListAdapter(this);//
    mRecyclerView.setAdapter(mAdapter);//



    //mAdapter.setOnItemClickListener(onItemClickListener);

    //isListView = true;

  }



    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();


    }

}