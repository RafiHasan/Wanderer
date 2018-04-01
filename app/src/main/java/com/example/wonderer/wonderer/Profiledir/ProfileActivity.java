package com.example.wonderer.wonderer.Profiledir;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Socialdir.dummytour;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.Plantour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Bottombarnav {

    private RecyclerView recyclerView;
    private ProfileAdapter adapter;


    public static String uid=Login.userid;

    public static List<Object> trips=new ArrayList<Object>();
    public static Profile showprofile= Login.userprofile;
     ImageView propic,logout;
     TextView name;
     TextView loc;
     TextView stat;
     TextView agent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        propic=(ImageView)findViewById(R.id.backdrop2);
        name=(TextView)findViewById(R.id.display_name);
        loc=(TextView)findViewById(R.id.place);
        stat=(TextView)findViewById(R.id.statusdetail);
        agent=(TextView)findViewById(R.id.agent);
        agent.setVisibility(View.GONE);

        adapter = new ProfileAdapter(this);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        Login.makeprofile.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showprofile=dataSnapshot.getValue(Profile.class);
                Picasso.with(getApplicationContext()).load(showprofile.Image).into(propic);
                name.setText(showprofile.Name);
                loc.setText(showprofile.Location);
                stat.setText(showprofile.Status);
                agent.setVisibility(View.VISIBLE);
                if(showprofile.isagent)
                {
                    agent.setText("Verified agent.");
                }
                else
                {   if(Login.userid.equals(uid))
                    agent.setText("Apply for agent.");
                    else
                    agent.setText("");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!showprofile.isagent)
                {
                    Uri uri = Uri.parse("http://practicetest1.azurewebsites.net/referrer/nihal"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });




        Login.root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trips=new ArrayList<Object>();


                List<dummytour> test=new ArrayList<dummytour>();
                List<Plantour>  plan=new ArrayList<Plantour>();

                DataSnapshot maketour=dataSnapshot.child("Newtrip");
                DataSnapshot plantour=dataSnapshot.child("Plantrip");
                Toast.makeText(getApplicationContext(),Login.userid+" "+uid,Toast.LENGTH_LONG).show();

                if(uid.equals(Login.userid))
                for (DataSnapshot imageSnapshot: plantour.getChildren()) {
                    Plantour d=imageSnapshot.getValue(Plantour.class);

                        d.tourid=imageSnapshot.getKey().toString();
                        if(d.Userid.equals(uid))
                        {
                            plan.add(d);

                        }


                }

                for (DataSnapshot imageSnapshot: maketour.getChildren()) {
                    dummytour d=imageSnapshot.getValue(dummytour.class);
                    d.tourid=imageSnapshot.getKey().toString();

                    if(d.userid.equals(uid))
                    {
                        test.add(d);

                    }
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
                        trips.add(test.get(k));
                        adapter.notifyDataSetChanged();
                        k++;
                    }
                    else if(k>=test.size())
                    {
                        trips.add(plan.get(j));
                        adapter.notifyDataSetChanged();
                        j++;
                    }
                    else
                    {
                        if(test.get(k).time>=plan.get(j).time)
                        {
                            trips.add(test.get(k));
                            adapter.notifyDataSetChanged();
                            k++;
                        }
                        else
                        {
                            trips.add(plan.get(j));
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



    /**
     * Adding few albums for testing
     */

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    */

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
