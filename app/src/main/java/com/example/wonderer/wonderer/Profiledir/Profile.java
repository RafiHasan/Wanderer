package com.example.wonderer.wonderer.Profiledir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rafi on 9/29/2017.
 */

public class Profile {

   public String Name="Darpon";
  public  String Image="https://firebasestorage.googleapis.com/v0/b/wonderer-af319.appspot.com/o/rafi%2Fdorpon.jpg?alt=media&token=a47573fb-7eb9-45ad-ac66-fb6797a123d2";
  public  String Location="Dhaka";
   // String Accounttype;
  public  String Status="I love tour";
  public  String fbid="";
  public  List<String> Completed=new ArrayList<String>();
    public  String userid="";
   // public  List<String> Plantour=new ArrayList<String>();

    public HashMap<String,String> Plantour=new HashMap<String, String>();
public boolean isagent=false;

  public  Profile()
    {

    }

   public Profile(String N,String I,String L,String S,String fb)
    {
        Name=N;
        Image=I;
        Location=L;
        Status=S;
        fbid=fb;
    }

}
