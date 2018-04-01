package com.example.wonderer.wonderer.plandir;

import com.example.wonderer.wonderer.Commentdir.usercomment;
import com.example.wonderer.wonderer.Socialdir.loc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafi on 9/29/2017.
 */

public class Place  {

    public String Name;
    public loc location=new loc();
    public String address="Someplace";
    public List<String> url=new ArrayList<String>();
    public String Description;
    public Double Rating=0.0;
    public String number="";
  //  List<String> Nearesthouses;
   // List<String> Nearestplaces;

    public  List<usercomment> comment=new ArrayList<usercomment>();


}


