package com.example.wonderer.wonderer.plandir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.loginregister.Login;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by megha on 15-03-06.
 */
public class Custom2nd extends RecyclerView.Adapter<Custom2nd.ViewHolder> {


    Context mContext;

   public static List<Place> placeList=new ArrayList<Place>();
    //OnItemClickListener mItemClickListener;

    private Custom2nd mAdapter;

    // 2
    public Custom2nd(Context context,List<Place> p) {
        this.mContext = context;
        placeList=p;
    }

    // 3
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView placeName;
        public ImageView placeImage;
        public TextView description;
        public TextView idnum;

        public ViewHolder(View itemView) {
            super(itemView);
            placeName=(TextView)itemView.findViewById(R.id.location_name);
            placeImage=(ImageView)itemView.findViewById(R.id.logout);
            description=(TextView)itemView.findViewById(R.id.location_description);
            idnum=(TextView)itemView.findViewById(R.id.textView4);
        }
    }



    // 1
    @Override
    public int getItemCount() {
        return placeList.size();
    }

    // need to change with card view in r.layout.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bond_demo, parent, false);
        return new ViewHolder(view);
    }

    // 3
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

         Place thisplace=placeList.get(position);

        holder.placeName.setText(thisplace.Name);

        holder.description.setText(thisplace.Description);

        holder.idnum.setText(String.valueOf(position+1+"."));
        if(thisplace.url.size()>0)
            Picasso.with(mContext).load(thisplace.url.get(0)).into(holder.placeImage);

             holder.placeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Login.userid.equals(TravelPlaceList.plan.Userid)) {
                    DetailLocation.thisplace = placeList.get(position);
                    Intent intent4 = new Intent(mContext, DetailLocation.class);
                    mContext.startActivity(intent4);
                }
            }
        });
    }


  /*public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      @Override
       public void onClick(View v) {

        }

        placeHolder.setOnClickListener(this);
  }*/





  /*Context mContext;
  OnItemClickListener mItemClickListener;

  public HomeAdapter(Context context) {
    this.mContext = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final Place place = new PlaceData().placeList().get(position);

    holder.placeName.setText(place.name);
    Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);

    /*Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), place.getImageResourceId(mContext));

    Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
      public void onGenerated(Palette palette) {
        int mutedLight = palette.getMutedColor(mContext.getResources().getColor(android.R.color.black));
        holder.placeNameHolder.setBackgroundColor(mutedLight);
      }
    });
  }

  @Override
  public int getItemCount() {
    return new PlaceData().placeList().size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public LinearLayout placeHolder;
    public LinearLayout placeNameHolder;
    public TextView placeName;
    public ImageView placeImage;

    public ViewHolder(View itemView) {
      super(itemView);
      placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
      placeName = (TextView) itemView.findViewById(R.id.placeName);
      placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
      placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
      placeHolder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      if (mItemClickListener != null) {
        mItemClickListener.onItemClick(itemView, getPosition());
      }
    }
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
    this.mItemClickListener = mItemClickListener;
  }
*/
}
