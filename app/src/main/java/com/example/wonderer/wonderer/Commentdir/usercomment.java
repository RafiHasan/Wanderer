package com.example.wonderer.wonderer.Commentdir;

import java.util.HashMap;

/**
 * Created by Rafi on 12/1/2017.
 */

public class usercomment
{
    public String userid;
    public String comment;
    public long time=1513703400000L;
    public HashMap<String,String> likemap=new HashMap<>();
    public usercomment()
    {}
    public usercomment(String u,String c,long t)
    {userid=u;
        comment=c;
    time=t;}
}
