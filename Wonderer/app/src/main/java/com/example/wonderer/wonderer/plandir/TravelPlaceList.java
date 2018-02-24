package com.example.wonderer.wonderer.plandir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.wonderer.wonderer.Homedir.Homelist;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.AIsystem;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TravelPlaceList extends Bottombarnav {



  private RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  private TravelListAdapter mAdapter;
    FloatingActionButton fab;

   public static int indicator=1;

  //private boolean isListView;
 // private Menu menu;

    public static DatabaseReference place;

 public static List<Place> placelist;
    public static List<Place> placelist2;

    public static List<Place> ai;

   public static Plantour plan=new Plantour();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

plan=new Plantour();
      fab=(FloatingActionButton)findViewById(R.id.fab);
      place= FirebaseDatabase.getInstance().getReference("Newplace");

      placelist=new ArrayList<Place>();
      ai=new ArrayList<Place>();
      place.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              for (DataSnapshot d:dataSnapshot.getChildren()) {
                  Place p=d.getValue(Place.class);
                  placelist.add(p);

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

              plan.homelist=Homelist.hometour;

              Main2Activity.make=false;
              AIsystem AI=new AIsystem();
              TravelPlaceList.ai=  AI.Closelocation(TravelPlaceList.placelist,TravelPlaceList.plan.Locationlist);
              placelist2=AI.Closelocation(TravelPlaceList.placelist);
              Toast.makeText(getApplicationContext(),String.valueOf(TravelPlaceList.ai.size()),Toast.LENGTH_LONG).show();
              Intent i=new Intent(getApplicationContext(),Main2Activity.class);
              startActivity(i);
          }
      });



    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);//

    mRecyclerView = (RecyclerView) findViewById(R.id.list);//
    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);//

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