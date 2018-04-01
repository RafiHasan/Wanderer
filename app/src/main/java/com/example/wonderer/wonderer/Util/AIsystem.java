package com.example.wonderer.wonderer.Util;

import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.Place;
import com.example.wonderer.wonderer.plandir.Plantour;
import com.example.wonderer.wonderer.plandir.TravelPlaceList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafi on 2/20/2018.
 */

public class AIsystem {
    List<Place> extraSelectplace=new ArrayList<Place>();
    List<Plantour> aiset=new ArrayList<Plantour>();

  public List<Place>  Closelocation(List<Place> AP,List<Place> SP)
  {extraSelectplace=new ArrayList<>();
      for(Place sp : SP)
      {
          for(Place ap : AP)
          {
              if(ap.Name.equals(sp.Name))
                  continue;
              if((sp.location.lan-ap.location.lan)*(sp.location.lan-ap.location.lan)*100*100+(sp.location.lat-ap.location.lat)*(sp.location.lat-ap.location.lat)*100*100<10)
              {
                  extraSelectplace.add(ap);
              }
          }
      }

      for (Place p:TravelPlaceList.plan.Locationlist) {
          extraSelectplace.remove(p);
      }

      return extraSelectplace;
  }



    public List<Place>  Closelocation(List<Place> AP)
    {
        aiset=new ArrayList<Plantour>();
        int[] arr={0,0,0,0,0,0,0,0,0,0,0,0};
        Login.plantour.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()) {

                    Plantour p=data.getValue(Plantour.class);
                    int k=0;
                    for(int i=0;i<TravelPlaceList.plan.Locationlist.size();i++)
                    {

                      if(  p.Locationlist.contains(TravelPlaceList.plan.Locationlist.get(i)))
                      {
                          k++;
                      }

                    }


                    if((k*100)/TravelPlaceList.plan.Locationlist.size()>50)
                     aiset.add(p);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for(int j=0;j<12;j++) {
            for (int i = 0; i < aiset.size(); i++) {

                if(AP.contains(AP.get(j)))
                break;
                if(aiset.get(i).Locationlist.contains(AP.get(j)))
                    arr[j]++;
            }
        }

        for(int i=0;i<12;i++)
        {
            for(int j=0;j<12;j++)
            {
                if(arr[i]>arr[j])
                {
                    int temp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;

                    Place p= AP.get(i);
                    AP.set(i,AP.get(j));
                    AP.set(j,p);

                }
            }

        }
        for (Place p:TravelPlaceList.plan.Locationlist) {
            AP.remove(p);
        }

        return AP;
    }


}
