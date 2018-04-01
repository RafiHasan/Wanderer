package com.example.wonderer.wonderer.Socialdir;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.example.wonderer.wonderer.gamenote.Notific;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.Plantour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends Bottombarnav {

    public static RecyclerView recyclerView;
    public static SocialAdapter adapter;

    public static int count=0;


    public static List<Object> Socialcontents=new ArrayList<Object>();

FloatingActionButton fab;
    int start =0;
    int end = 100;

    ImageView play,notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.social_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new SocialAdapter(this);


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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Login.root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Socialcontents=new ArrayList<Object>();
                List<dummytour> test=new ArrayList<dummytour>();
                List<Plantour>  plan=new ArrayList<Plantour>();
                DataSnapshot maketour=dataSnapshot.child("Newtrip");
                DataSnapshot plantour=dataSnapshot.child("Plantrip");

                for (DataSnapshot imageSnapshot: plantour.getChildren()) {
                    Plantour d=imageSnapshot.getValue(Plantour.class);
                    if(d.showing.equals("Yes"))
                    {
                        d.tourid=imageSnapshot.getKey().toString();
                        plan.add(d);
                    }
                }

                for (DataSnapshot imageSnapshot: maketour.getChildren()) {
                            dummytour d=imageSnapshot.getValue(dummytour.class);
                            d.tourid=imageSnapshot.getKey().toString();
                            test.add(d);
                        }



                        for(int i=0;i<plan.size();i++)
                        {
                            for(int j=0;j<plan.size()-1;j++)
                            {

                                if(plan.get(j).time<plan.get(j+1).time)
                                {   Plantour p=plan.get(j);
                                    Plantour p2=plan.get(j+1);
                                    plan.set(j,p2);
                                    plan.set(j+1,p);

                                }

                            }
                        }

                for(int i=0;i<test.size();i++)
                {
                    for(int j=0;j<test.size()-1;j++)
                    {
                        if(test.get(j).time<test.get(j+1).time)
                        {   dummytour p=test.get(j);
                            dummytour p2=test.get(j+1);
                            test.set(j,p2);
                            test.set(j+1,p);

                        }
                    }
                }



                int j=0;
                int k=0;

                for(int i=0;i<plan.size()+test.size();i++)
                {
                    if(j>=plan.size())
                    {
                      Socialcontents.add(test.get(k));
                        adapter.notifyDataSetChanged();
                        k++;
                    }
                    else if(k>=test.size())
                    {
                        Socialcontents.add(plan.get(j));
                        adapter.notifyDataSetChanged();
                        j++;
                    }
                    else
                    {
                        if(test.get(k).time>=plan.get(j).time)
                        {
                            Socialcontents.add(test.get(k));
                            adapter.notifyDataSetChanged();
                            k++;
                        }
                        else
                        {
                            Socialcontents.add(plan.get(j));
                            adapter.notifyDataSetChanged();
                            j++;
                        }
                    }

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }



    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




}
