package com.example.wonderer.wonderer.Profiledir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wonderer.wonderer.Completetourdir.Completetouractivity;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Socialdir.dummytour;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.Main2Activity;
import com.example.wonderer.wonderer.plandir.Plantour;
import com.example.wonderer.wonderer.plandir.TravelPlaceList;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private Context mContext;

    public static String pop="";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, likecount,comme,time;
        public ImageView thumbnail, overflow ,profilephoto,heart,heartred,comicon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            likecount = (TextView) view.findViewById(R.id.likecount);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            comme=(TextView)view.findViewById(R.id.image_comments_link);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            profilephoto=(ImageView)view.findViewById(R.id.profile_photo);
            heart=(ImageView)view.findViewById(R.id.image_heart);
            heartred=(ImageView)view.findViewById(R.id.image_heart_red);
            time=(TextView)view.findViewById(R.id.image_time_posted);
            comicon=(ImageView)view.findViewById(R.id.speech_bubble);
        }
    }


    public ProfileAdapter(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.heart.setVisibility(View.GONE);
        holder.heartred.setVisibility(View.GONE);
        holder.comicon.setVisibility(View.GONE);
        holder.comme.setVisibility(View.GONE);
        holder.time.setVisibility(View.VISIBLE);

        if(ProfileActivity.trips.get(position) instanceof dummytour )
        {
            final dummytour com= (dummytour) ProfileActivity.trips.get(position);


            long t=System.currentTimeMillis();

            if(t-com.time<300000)
            {
                holder.time.setText("Just Now");
            }

            else if(t-com.time<86400000)
            {
                long yourmilliseconds = t-com.time;
                String time="";
                yourmilliseconds/=60000;
                time=String.valueOf(yourmilliseconds%60)+" Minute";
                yourmilliseconds/=60;
                if(yourmilliseconds>0)
                    time=String.valueOf(yourmilliseconds)+" Hour";
                holder.time.setText(time+" Ago");
            }
            else if(t-com.time<172800000)
            {
                holder.time.setText("Yesterday");
            }
            else
            {
                long yourmilliseconds = com.time;
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
                Date resultdate = new Date(yourmilliseconds);
                holder.time.setText(sdf.format(resultdate));
            }




            holder.likecount.setText(com.likemap.size() + " likes");
            holder.comme.setText(com.comment.size()+" Comments");
            Picasso.with(mContext).load(com.url.get(0)).into(holder.thumbnail);
            holder.title.setText("Completed");

            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Completetouractivity.showtour=com;

                    Intent i=new Intent(mContext,Completetouractivity.class);
                    mContext.startActivity(i);
                }
            });

        }
        else
        {
            final Plantour pla=(Plantour) ProfileActivity.trips.get(position);



            long t=System.currentTimeMillis();

            if(t-pla.time<300000)
            {
                holder.time.setText("Just Now");
            }

            else if(t-pla.time<86400000)
            {
                long yourmilliseconds = t-pla.time;
                String time="";
                yourmilliseconds/=60000;
                time=String.valueOf(yourmilliseconds%60)+" Minute";
                yourmilliseconds/=60;
                if(yourmilliseconds>0)
                    time=String.valueOf(yourmilliseconds)+" Hour";
                holder.time.setText(time+" Ago");
            }
            else if(t-pla.time<172800000)
            {
                holder.time.setText("Yesterday");
            }
            else
            {
                long yourmilliseconds = pla.time;
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
                Date resultdate = new Date(yourmilliseconds);
                holder.time.setText(sdf.format(resultdate));
            }


            Picasso.with(mContext).load(pla.Locationlist.get(0).url.get(0)).into(holder.thumbnail);
            holder.title.setText("Planned");
            holder.likecount.setText(pla.comment.size()+" Comments");
            holder.comme.setText("Click to see details");

            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Plantour plan=pla;
                    TravelPlaceList.plan=plan;
                    if(ProfileActivity.uid.equals(Login.userid))
                    Main2Activity.make=true;
                    else
                    Main2Activity.make=false;
                    Intent i=new Intent(mContext,Main2Activity.class);
                    mContext.startActivity(i);
                }
            });

            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop=pla.tourid;
                    showPopupMenu(v);
                }
            });

        }

        Picasso.with(mContext).load(ProfileActivity.showprofile.Image).into(holder.profilephoto);




    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                {
                    Login.plantour.child(pop).child("showing").setValue("Yes");
                    Login.plantour.child(pop).child("time").setValue(System.currentTimeMillis());
                }
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {

            return ProfileActivity.trips.size();
    }
}
