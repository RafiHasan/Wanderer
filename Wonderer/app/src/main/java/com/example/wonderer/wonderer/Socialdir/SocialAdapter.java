package com.example.wonderer.wonderer.Socialdir;

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

import com.example.wonderer.wonderer.Commentdir.CommentActivity;
import com.example.wonderer.wonderer.Completetourdir.Completetouractivity;
import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.Profiledir.ProfileActivity;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.Main2Activity;
import com.example.wonderer.wonderer.plandir.Plantour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {

    public int pop=0;

    private Context mContext;

    public static dummytour d;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, likecount,comme,time;
        public ImageView thumbnail, overflow ,profilephoto,heart,heartred,commenticon;

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
            commenticon=(ImageView)view.findViewById(R.id.speech_bubble);
        }
    }


    public SocialAdapter(Context mContext) {
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

   if(SocialActivity.Socialcontents.get(position) instanceof dummytour )
   {


       final dummytour social= (dummytour) SocialActivity.Socialcontents.get(position);

       Login.makeprofile.child(social.userid).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
            final Profile  pro=dataSnapshot.getValue(Profile.class);
               holder.title.setText(pro.Name+"'s Complete trip");
               Picasso.with(mContext).load(pro.Image).resize(20,20).centerCrop().into(holder.profilephoto);

               holder.title.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       ProfileActivity.showprofile=pro;
                       ProfileActivity.uid=social.userid;
                       Bottombarnav.x=4;
                       Intent i=new Intent(mContext,ProfileActivity.class);
                       mContext.startActivity(i);
                   }
               });

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



       long t=System.currentTimeMillis();

       if(t-social.time<300000)
       {
           holder.time.setText("Just Now");
       }

       else if(t-social.time<86400000)
       {
           long yourmilliseconds = t-social.time;
           String time="";
           yourmilliseconds/=60000;
           time=String.valueOf(yourmilliseconds%60)+" Minute";
           yourmilliseconds/=60;
           if(yourmilliseconds>0)
               time=String.valueOf(yourmilliseconds)+" Hour";
           holder.time.setText(time+" Ago");
       }
       else if(t-social.time<172800000)
       {
           holder.time.setText("Yesterday");
       }
       else
       {
           long yourmilliseconds = social.time;
           SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
           Date resultdate = new Date(yourmilliseconds);
           holder.time.setText(sdf.format(resultdate));
       }



       holder.likecount.setText("Liked By "+social.likemap.size()+" Person");
       holder.comme.setText("View "+social.comment.size()+" Comments");
       // loading album cover using Glide library
       //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
       Picasso.with(mContext).load(social.url.get(0)).resize(100,100).centerCrop().into(holder.thumbnail);

       holder.heart.setVisibility(View.VISIBLE);
       holder.heartred.setVisibility((View.INVISIBLE));
       for (String k:social.likemap.keySet()) {
           String val= social.likemap.get(k);
           if(val.equals(Login.userid))
           {
               holder.heart.setVisibility(View.INVISIBLE);
               holder.heartred.setVisibility((View.VISIBLE));
               break;
           }

       }

       holder.heart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Login.maketour.child(social.tourid).child("likemap").push().setValue(Login.userid);

           }
       });

       holder.heartred.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Login.maketour.child(social.tourid).child("likemap").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                           String d=imageSnapshot.getValue(String.class);
                           String key=imageSnapshot.getKey();
                           if(d.equals(Login.userid))
                           {
                               Login.maketour.child(social.tourid).child("likemap").child(key).removeValue();

                           }
                       }


                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });





           }
       });

       holder.comme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               CommentActivity.position=position;
               CommentActivity.comref=Login.maketour.child(social.tourid).child("comment");
               Intent i=new Intent(mContext,CommentActivity.class);
               mContext.startActivity(i);
           }
       });

       holder.thumbnail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               CommentActivity.position=position;

               d=social;

               Completetouractivity.showtour=d;

               Intent i=new Intent(mContext,Completetouractivity.class);
               mContext.startActivity(i);
           }
       });


       holder.overflow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showPopupMenu(holder.overflow,position);
           }
       });


   }





   else
   {

       holder.heart.setVisibility(View.GONE);
       holder.heartred.setVisibility(View.GONE);
       holder.commenticon.setVisibility(View.GONE);
       holder.likecount.setVisibility(View.GONE);



     final Plantour plan= (Plantour) SocialActivity.Socialcontents.get(position);

       Login.makeprofile.child(plan.Userid).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
             final Profile  pro=dataSnapshot.getValue(Profile.class);

               holder.title.setText(pro.Name+"'s Planed trip");

               holder.title.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       ProfileActivity.showprofile=pro;
                       ProfileActivity.uid=plan.Userid;
                       Bottombarnav.x=4;
                       Intent i=new Intent(mContext,ProfileActivity.class);
                       mContext.startActivity(i);
                   }
               });

               Picasso.with(mContext).load(pro.Image).resize(20,20).centerCrop().into(holder.profilephoto);


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


       long t=System.currentTimeMillis();

       if(t-plan.time<300000)
       {
           holder.time.setText("Just Now");
       }

       else if(t-plan.time<86400000)
       {
           long yourmilliseconds = t-plan.time;
           String time="";
           yourmilliseconds/=60000;
           time=String.valueOf(yourmilliseconds%60)+" Minute";
           yourmilliseconds/=60;
           if(yourmilliseconds>0)
               time=String.valueOf(yourmilliseconds)+" Hour";
           holder.time.setText(time+" Ago");
       }
       else if(t-plan.time<172800000)
       {
           holder.time.setText("Yesterday");
       }
       else
       {
           long yourmilliseconds = plan.time;
           SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
           Date resultdate = new Date(yourmilliseconds);
           holder.time.setText(sdf.format(resultdate));
       }


       Picasso.with(mContext).load(plan.Locationlist.get(0).url.get(0)).resize(100,100).centerCrop().into(holder.thumbnail);



       holder.thumbnail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Main2Activity.showplantour=plan;
               Intent i=new Intent(mContext,Main2Activity.class);
               mContext.startActivity(i);
           }
       });

       holder.comme.setText("View "+plan.comment.size()+" Comments");

       holder.comme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               CommentActivity.position=position;
               CommentActivity.comref=Login.plantour.child(plan.tourid).child("comment");

               Intent i=new Intent(mContext,CommentActivity.class);
               mContext.startActivity(i);
           }
       });

   }





    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,int position) {
        // inflate menu

        pop=position;
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
                    Toast.makeText(mContext, "Add to favourite"+String.valueOf(pop), Toast.LENGTH_SHORT).show();
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

        return SocialActivity.Socialcontents.size();
    }





}
