package com.example.wonderer.wonderer;

import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

/**
 * Created by Rafi on 9/29/2017.
 */

public class CompleteTrip {
    String Userid;
    String CompleteTripid;
    String Tourrating;
    List<timelocationpair> Cordinates;
    List<imagelocationpair> Images;
    List<statuslocationpair> Statuses;
    List<videolocationpair> Videos;
    List<String> Locationlist;
    List<usercomment> Comment;

    class imagelocationpair{
        LatLng location;
        Bitmap image;
        Date date;
    }
    class statuslocationpair{
        LatLng location;
        String status;
        Date date;
    }
    class videolocationpair{
        LatLng location;
        MediaStore.Video video;
        Date date;
    }
    class timelocationpair{
        LatLng location;
        Date date;
    }
    class usercomment
    {
        String userid;
        String comment;
    }

}
