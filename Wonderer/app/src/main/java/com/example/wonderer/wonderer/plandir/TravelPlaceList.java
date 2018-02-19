package com.example.wonderer.wonderer.plandir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.wonderer.wonderer.Homedir.Homelist;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Socialdir.dummytour;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TravelPlaceList extends Bottombarnav {





    public static dummytour showtour;


  private RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  private TravelListAdapter mAdapter;
    FloatingActionButton fab;
  //private boolean isListView;
 // private Menu menu;

    public static DatabaseReference place;
 public static List<Place> placelist;
    public static List<Place> plantour;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


      fab=(FloatingActionButton)findViewById(R.id.fab);
      place= FirebaseDatabase.getInstance().getReference("Newplace");

      placelist=new ArrayList<Place>();
      plantour=new ArrayList<Place>();
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

              Plantour plan=new Plantour(plantour,Homelist.hometour);

              Main2Activity.showplantour=plan;
              Main2Activity.make=false;
              Intent i=new Intent(getApplicationContext(),Main2Activity.class);
              startActivity(i);
          }
      });

   /* toolbar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(Botombarapp.istrival==0) {
          List<Vertex> loc = new ArrayList<Vertex>();
          for (Vertex e : Botombarapp.VisitableLocation) {
            if (e.selected) {
              loc.add(e);
            }
          }
          Trip t = new Trip(loc);

          Botombarapp.Mytrip.add(t);

          Intent intent4 = new Intent(Homelist.this, MapsActivity.class);
          startActivity(intent4);
        }
      }
    });*/
    //setUpActionBar();

    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);//

    mRecyclerView = (RecyclerView) findViewById(R.id.list);//
    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);//

    mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
    mAdapter = new TravelListAdapter(this);//
    mRecyclerView.setAdapter(mAdapter);//



    //mAdapter.setOnItemClickListener(onItemClickListener);

    //isListView = true;

  }


}