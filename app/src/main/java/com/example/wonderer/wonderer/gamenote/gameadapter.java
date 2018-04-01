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

/**
 * Created by Rafi on 3/4/2018.
 */

public class gameadapter extends RecyclerView.Adapter<gameadapter.MyViewHolder> {

    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name,spot_text,km_text,rank;
        public ImageView profile;
        public MyViewHolder(View view) {
            super(view);

            user_name=(TextView)view.findViewById(R.id.user_name11);
            spot_text=(TextView)view.findViewById(R.id.spot_text11);
            km_text=(TextView)view.findViewById(R.id.km_text11);
            profile=(ImageView)view.findViewById(R.id.profile_image11);
            rank=(TextView)view.findViewById(R.id.rank123456);
        }
    }


    public gameadapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public gameadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_card, parent, false);

        return new gameadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Profile pro=Game.profiles.get(position);

        Picasso.with(mContext).load(pro.Image).resize(50,50).centerCrop().into(holder.profile);
        holder.user_name.setText(pro.Name);
        holder.spot_text.setText(29-position+" spots");
        holder.km_text.setText(500-position*13+" km");
        holder.rank.setText(String.valueOf(position+1));
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

        return Game.profiles.size();
    }





}

