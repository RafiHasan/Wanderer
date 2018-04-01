package com.example.wonderer.wonderer.Socialdir;

import android.location.Location;

import com.example.wonderer.wonderer.Commentdir.usercomment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafi on 11/22/2017.
 */

public class dummytour {

    public  String userid="rafi";
    public  String tourid;
    public  List <loc> travel=new ArrayList<loc>();
    public  List <loc> photo=new ArrayList<loc>();
    public  List <String> Like=new ArrayList<String>();

    public HashMap<String,String> likemap=new HashMap<>();
    public  List<String> url=new ArrayList<String>();

    public  List<usercomment> comment=new ArrayList<usercomment>();

    public long  time=1513703400000L;


    public dummytour()
    {

    }

    public dummytour(List<Location> loc,List<Location> pic,List<String> urlin)
    {
        url=urlin;
        for (Location l:loc) {
            loc nloc=new loc();
            nloc.lat=l.getLatitude();
            nloc.lan=l.getLongitude();
            travel.add(nloc);
        }
        for (Location l:pic) {
            loc nloc=new loc();
            nloc.lat=l.getLatitude();
            nloc.lan=l.getLongitude();
            photo.add(nloc);
        }
       // travel=loc;
      //  photo=pic;

    }


}

