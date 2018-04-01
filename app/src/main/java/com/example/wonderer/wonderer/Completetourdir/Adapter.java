package com.example.wonderer.wonderer.Completetourdir;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wonderer.wonderer.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public Adapter() {

    }

    android.support.v7.app.AlertDialog.Builder mBuilder;
    android.support.v7.app.AlertDialog dialog;
    ImageView img;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView placeName,placestatus;
        public ImageView img1,img2;


        public ViewHolder(View itemView) {
            super(itemView);
            placeName=(TextView)itemView.findViewById(R.id.name);
            img1=(ImageView)itemView.findViewById(R.id.one);
            placestatus=(TextView)itemView.findViewById(R.id.status);
            img2=(ImageView)itemView.findViewById(R.id.two);

        }
    }



    // 1
    @Override
    public int getItemCount() {
        return Completetouractivity.showtour.photo.size();
    }

    // need to change with card view in r.layout.
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
        return new Adapter.ViewHolder(view);

    }

    // 3
    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, final int position) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(Completetouractivity.c, Locale.getDefault());
        holder.placeName.setText("In Road");
        holder.placestatus.setText("Photo in road");
        try {
            addresses = geocoder.getFromLocation(Completetouractivity.showtour.photo.get(position).lat, Completetouractivity.showtour.photo.get(position).lan, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            if(!knownName.equals(null))
            {
                holder.placeName.setText(knownName);
                holder.placestatus.setText("Photo at "+knownName);
            }
            else
            {
                holder.placeName.setText("In Road");
                holder.placestatus.setText("Photo in road");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


             holder.img1.setVisibility(View.GONE);
             Picasso.with(Completetouractivity.c).load(Completetouractivity.showtour.url.get(position)).into(holder.img2);

             holder.img1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {





                     mBuilder = new android.support.v7.app.AlertDialog.Builder(Completetouractivity.c);
                     mBuilder.setView(Completetouractivity.mView);

                     dialog = mBuilder.create();
                     img= (ImageView) Completetouractivity.mView.findViewById(R.id.fulpic);
                     Picasso.with(Completetouractivity.c).load(Completetouractivity.showtour.url.get(position)).into(img);

                     dialog.show();




                 }
             });








    }
}

