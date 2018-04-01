package com.example.wonderer.wonderer.plandir;

import com.example.wonderer.wonderer.Commentdir.usercomment;
import com.example.wonderer.wonderer.Homedir.Home;
import com.example.wonderer.wonderer.loginregister.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafi on 12/20/2017.
 */

public class Plantour {

   public String Userid= Login.userid;
   public List<Place> Locationlist=new ArrayList<Place>();
    public  String tourid;
    public List<Home> homelist=new ArrayList<Home>();

    public String showing="No";
    public long time=1513703400000L;

    public  List<usercomment> comment=new ArrayList<usercomment>();


  public  Plantour()
    {

    }
   public Plantour(List<Place> l,List<Home> h)
    {
        Locationlist=l;
        homelist=h;
    }

}
