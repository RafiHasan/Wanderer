package com.example.wonderer.wonderer.gamenote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.Profiledir.ProfileActivity;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.Util.Bottombarnav;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by Rafi on 3/4/2018.
 */

public class notiadapter extends RecyclerView.Adapter<notiadapter.MyViewHolder> {

    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name,time1,action;
        public ImageView profile;
        public MyViewHolder(View view) {
            super(view);

            user_name=(TextView)view.findViewById(R.id.user_name);
            time1=(TextView)view.findViewById(R.id.time);
            profile=(ImageView)view.findViewById(R.id.profile_image);
            action=(TextView)view.findViewById(R.id.action);
        }
    }


    public notiadapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public notiadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_card, parent, false);

        return new notiadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Profile pro=Notific.profiles.get(position);

        Picasso.with(mContext).load(pro.Image).resize(50,50).centerCrop().into(holder.profile);
        holder.user_name.setText(pro.Name);


        Random rand = new Random();

        int  n = rand.nextInt(50);

        if(n%3==0)
        {
            holder.action.setText("Likes your trip.");
        }
        else if(n%3==1)
        {
            holder.action.setText("Commented on your trip.");
        }
        else
        {
            holder.action.setText("Wants to join your trip.");
        }

        if(position==0)
            holder.time1.setText("Just Now");
        else if(position==1)
            holder.time1.setText("3 hours ago");
        else if(position==2)
            holder.time1.setText("Yesterday");
        else
        holder.time1.setText(position-1+" Days ago");

       // holder.time1.setText();
        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.uid=pro.userid;
                Bottombarnav.x=4;
                Intent i=new Intent(mContext,ProfileActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return Notific.profiles.size();
    }





}

