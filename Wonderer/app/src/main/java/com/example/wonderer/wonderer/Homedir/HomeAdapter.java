package com.example.wonderer.wonderer.Homedir;

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

import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.plandir.TravelPlaceList;
import com.squareup.picasso.Picasso;

/**
 * Created by megha on 15-03-06.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


  Context mContext;

  //OnItemClickListener mItemClickListener;

  private HomeAdapter mAdapter;

  // 2
  public HomeAdapter(Context context) {
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
    return Homelist.homelist.size();
  }

  // need to change with card view in r.layout.
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip, parent, false);
    return new ViewHolder(view);
  }

  // 3
  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {

final Home thishome= Homelist.homelist.get(position);
      holder.rating.setRating(4);
      holder.distance.setVisibility(View.GONE);
      holder.reviu.setVisibility(View.GONE);
    holder.placeName.setText(thishome.address);
   // holder.reviu.setText(String.valueOf(placename.rater));
    holder.description.setText(thishome.Description);
   // holder.rating.setRating(placename.rating);
   // holder.selection.setChecked(placename.selected);
      holder.selection.setVisibility(View.VISIBLE);
   /* holder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(holder.selection.isChecked())
            Homelist.Plantour.add(thisplace);
        else
            Homelist.Plantour.remove(thisplace);
      }
    });*/

      holder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(holder.selection.isChecked())
                  Homelist.hometour.add(thishome);
              else
                  Homelist.hometour.remove(thishome);
          }
      });

    if(thishome.url.size()>0)
      Picasso.with(mContext).load(thishome.url.get(0)).into(holder.placeImage);
   // Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);


    holder.placeImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Detailhome.thishome= Homelist.homelist.get(position);
        Intent intent4 = new Intent(mContext, Detailhome.class);
        mContext.startActivity(intent4);
      }
    });
  }


}
