package com.example.wonderer.wonderer.gamenote;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Socialdir.dummytour;
import com.example.wonderer.wonderer.loginregister.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafi on 3/4/2018.
 */

public class Notific extends Activity {

    public static DatabaseReference notify;
    public static RecyclerView recyclerView;
    public static notiadapter adapter;

    public static List<Profile> profiles=new ArrayList<Profile>();
    public static List<notice> notification=new ArrayList<notice>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification_page);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new notiadapter(this);

        notify= FirebaseDatabase.getInstance().getReference("Newnotify");

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Login.maketour.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profiles=new ArrayList<Profile>();

                for (DataSnapshot d:dataSnapshot.getChildren()) {

                    dummytour p=d.getValue(dummytour.class);

                    if(Login.userid.equals(p.userid))
                    {
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

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Login.plantour.addListenerForSingleValueEvent(new ValueEventListener() {
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
