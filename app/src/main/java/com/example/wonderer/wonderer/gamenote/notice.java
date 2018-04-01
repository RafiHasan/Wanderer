package com.example.wonderer.wonderer.gamenote;

import com.example.wonderer.wonderer.Profiledir.Profile;

/**
 * Created by Rafi on 3/28/2018.
 */

public class notice {

    public long time=1513703400000L;
    public Profile profile=new Profile();
    public String typ="None";
    public int count=0;
    notice(long t,Profile n,int c,String ty)
    {
        time=t;
        profile=n;
        count=c;
        typ=ty;
    }

}
