package com.example.wonderer.wonderer.Homedir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.Main2Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Homelist extends Bottombarnav {


public static List<Home> homelist=new ArrayList<Home>();

    public static List<Home> hometour=new ArrayList<Home>();

  private RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  private HomeAdapter mAdapter;
    FloatingActionButton fab;
  //private boolean isListView;
 // private Menu menu;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);



      fab=(FloatingActionButton)findViewById(R.id.fab);


      homelist=new ArrayList<Home>();


      Login.home.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              for (DataSnapshot d:dataSnapshot.getChildren()) {
                  Home h=d.getValue(Home.class);
                  homelist.add(h);

              }


          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });





      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent i=new Intent(getApplicationContext(),Main2Activity.class);
              startActivity(i);
          }
      });



    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);//

    mRecyclerView = (RecyclerView) findViewById(R.id.list);//
    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);//

    mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
    mAdapter = new HomeAdapter(this);//
    mRecyclerView.setAdapter(mAdapter);//



    //mAdapter.setOnItemClickListener(onItemClickListener);

    //isListView = true;

  }


}