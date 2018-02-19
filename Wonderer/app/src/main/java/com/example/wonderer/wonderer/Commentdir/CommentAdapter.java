package com.example.wonderer.wonderer.Commentdir;

import android.content.Context;
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

import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private Context mContext;
    private List<usercomment> comment;
    List<Profile> comentprof;
    private ImageView mHeartRed, mHeartWhite, mComment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleComment, countComment ,time;
        public ImageView thumbnailComment, overflowComment;

        public MyViewHolder(View view) {
            super(view);
            titleComment = (TextView) view.findViewById(R.id.comment_username);
            countComment = (TextView) view.findViewById(R.id.comment);
            thumbnailComment = (ImageView) view.findViewById(R.id.comment_profile_image);
            overflowComment = (ImageView) view.findViewById(R.id.comment_like);
               time=(TextView)view.findViewById(R.id.comment_time_posted);
            //mComment = (ImageView) view.findViewById(R.id.speech_bubble);


        }
    }


    public CommentAdapter(Context mContext, List<usercomment> com, List<Profile> p) {
        this.mContext = mContext;
        comment = com;
        comentprof=p;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_comment, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        com.example.wonderer.wonderer.Commentdir.usercomment comm = comment.get(comment.size()-position-1);

        holder.countComment.setText(comm.comment);

        long t=System.currentTimeMillis();

        if(t- comment.get(position).time<300000)
        {
            holder.time.setText("Just Now");
        }

        else if(t-comment.get(position).time<86400000)
        {
            long yourmilliseconds = t-comment.get(position).time;
            String time="";
            yourmilliseconds/=60000;
            time=String.valueOf(yourmilliseconds%60)+" Minute";
            yourmilliseconds/=60;
            if(yourmilliseconds>0)
                time=String.valueOf(yourmilliseconds)+" Hour";
            holder.time.setText(time+" Ago");
        }
        else if(t-comment.get(position).time<172800000)
        {
            holder.time.setText("Yesterday");
        }
        else
        {
            long yourmilliseconds = comment.get(position).time;
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
            Date resultdate = new Date(yourmilliseconds);
            holder.time.setText(sdf.format(resultdate));
        }


        if(position<comentprof.size())
        {  holder.titleComment.setText(comentprof.get(comentprof.size()-1-position).Name);
        Picasso.with(mContext).load(comentprof.get(comentprof.size()-1-position).Image).into(holder.thumbnailComment);}


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
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
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
        return CommentActivity.comment.size();
    }


}
