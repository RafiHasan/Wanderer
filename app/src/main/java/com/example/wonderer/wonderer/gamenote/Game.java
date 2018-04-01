package com.example.wonderer.wonderer.gamenote;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.example.wonderer.wonderer.loginregister.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafi on 3/4/2018.
 */

public class Game extends Bottombarnav {
    public static RecyclerView recyclerView;
    public static gameadapter adapter;

    public static List<Profile> profiles=new ArrayList<Profile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new gameadapter(this);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Login.makeprofile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profiles=new ArrayList<Profile>();

                for (DataSnapshot d:dataSnapshot.getChildren()) {

                    Profile p=d.getValue(Profile.class);

                   p.userid= d.getKey().toString();

                    profiles.add(p);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
