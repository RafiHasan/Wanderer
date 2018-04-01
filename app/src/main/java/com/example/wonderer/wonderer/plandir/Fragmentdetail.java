package com.example.wonderer.wonderer.plandir;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wonderer.wonderer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by filip on 8/21/2015.
 */
public class Fragmentdetail extends Fragment {

    View v;
    ImageView Img;
    String Url;
   Context context;
    public Fragmentdetail()
    {

    }
  public void Fragmentload(String url, Context c)
    {
        Url=url;
        context=c;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragdetail,container,false);

        Img=(ImageView)v.findViewById(R.id.logout);
        //image.setHeight(Img.getHeight());
        //image.setWidth(Img.getWidth());
        Picasso.with(context).load(Url).into(Img);
        return v;
    }


}
