package com.example.wonderer.wonderer.plandir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wonderer.wonderer.R;
import com.squareup.picasso.Picasso;

/**
 * Created by megha on 15-03-06.
 */
public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {


  Context mContext;

  //OnItemClickListener mItemClickListener;

  private TravelListAdapter mAdapter;

  // 2
  public TravelListAdapter(Context context) {
    this.mContext = context;
  }

  // 3
  public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView placeName;
    public ImageView placeImage;
    public RatingBar rating;
    public Switch selection;
    public TextView description;
    public TextView reviu;
    public TextView distance;

    public ViewHolder(View itemView) {
      super(itemView);
        placeName=(TextView)itemView.findViewById(R.id.tv_card_main_2_title);
      placeImage=(ImageView)itemView.findViewById(R.id.img_main_card_2);
     rating=(RatingBar) itemView.findViewById(R.id.ratingBar);
      selection=(Switch) itemView.findViewById(R.id.switch2);
      description=(TextView)itemView.findViewById(R.id.textView2);
      reviu=(TextView)itemView.findViewById(R.id.textView3);
      distance=(TextView)itemView.findViewById(R.id.textView);
    }
  }



  // 1
  @Override
  public int getItemCount() {
    return TravelPlaceList.placelist.size();
  }

  // need to change with card view in r.layout.
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
   // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip, parent, false);
   // return new ViewHolder(view);
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.trip, parent, false);

    return new ViewHolder(itemView);
  }

  // 3
  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {

final Place thisplace=TravelPlaceList.placelist.get(position);


    holder.placeName.setText(thisplace.Name);
   // holder.reviu.setText(String.valueOf(placename.rater));
    holder.description.setText(thisplace.Description);
   // holder.rating.setRating(placename.rating);
   // holder.selection.setChecked(placename.selected);
      holder.selection.setVisibility(View.VISIBLE);

      holder.selection.setChecked(false);

      if(TravelPlaceList.plan.Locationlist.contains(thisplace))
      {
          holder.selection.setChecked(true);
      }

    holder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(holder.selection.isChecked())
        {if(!TravelPlaceList.plan.Locationlist.contains(thisplace))
            {
                TravelPlaceList.plan.Locationlist.add(thisplace);
            }
            Toast.makeText(mContext,"ok"+thisplace.Name,Toast.LENGTH_LONG).show();}
        else
        { TravelPlaceList.plan.Locationlist.remove(thisplace);
            Toast.makeText(mContext,"del"+thisplace.Name,Toast.LENGTH_LONG).show();}
      }
    });

    if(thisplace.url.size()>0)
      Picasso.with(mContext).load(thisplace.url.get(0)).into(holder.placeImage);
   // Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);


    holder.placeImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          DetailLocation.thisplace=TravelPlaceList.placelist.get(position);
        Intent intent4 = new Intent(mContext, DetailLocation.class);
        mContext.startActivity(intent4);
      }
    });

  }



}
