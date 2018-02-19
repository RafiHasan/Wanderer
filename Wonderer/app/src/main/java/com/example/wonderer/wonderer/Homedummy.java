package com.example.wonderer.wonderer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wonderer.wonderer.Profiledir.Profile;
import com.example.wonderer.wonderer.Socialdir.dummytour;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.TravelPlaceList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafi on 11/21/2017.
 */

public class Homedummy extends AppCompatActivity {
    Button mapview;
    Button image;
    Button start;
    Button stop;
    Button update;
    public static DatabaseReference maketour;
    public static DatabaseReference makeprofile;
    List<String> url=new ArrayList<String>();
    public static List <Location> travel=new ArrayList<Location>();
    List <Location> photo=new ArrayList<Location>();
    List<Bitmap> bit=new ArrayList<Bitmap>();
    public static List<String> tripid=new ArrayList<String>();
   public static List<dummytour> socialdata=new ArrayList<dummytour>();

    public static Profile mine=new Profile();

    public static List<Profile> mine2=new ArrayList<Profile>();
 String id="55";
    int check=0;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homedummy);


        maketour= FirebaseDatabase.getInstance().getReference("Newtrip");
        makeprofile=FirebaseDatabase.getInstance().getReference("Newuser");



        mapview=(Button)findViewById(R.id.mapview);
        image=(Button)findViewById(R.id.camera);
        start=(Button)findViewById(R.id.start);
        stop=(Button)findViewById(R.id.stop);
        update=(Button)findViewById(R.id.update);
        mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         Intent i=new Intent(getApplicationContext(), TravelPlaceList.class);
                startActivity(i);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,0);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    List<Location> l = Backgroundlocation.l;
                    Toast.makeText(getApplicationContext(),String.valueOf(l.size()),Toast.LENGTH_LONG).show();
                    Intent b=new Intent(getApplicationContext(),Backgroundlocation.class);
                    startService(b);
                }
                catch(Exception e)
                {Intent b=new Intent(getApplicationContext(),Backgroundlocation.class);
                    startService(b);}

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                maketour.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      for(DataSnapshot d:dataSnapshot.getChildren())
                        { tripid.add(d.getKey());
                        Log.e("test",d.getKey().toString());}
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(i=0;i<bit.size();i++)
                {
                    Bitmap bitmap = bit.get(i);
                    // i.setImageBitmap(bitmap);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference mountainImagesRef = storageRef.child("rafi/"+String.valueOf(i)+".jpg");
                    UploadTask uploadTask = mountainImagesRef.putBytes(data);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String down=taskSnapshot.getDownloadUrl().toString();
                            url.add(down);
                          check++;

                            if(check==bit.size())
                            {

                                String id= maketour.push().getKey();
                                dummytour d=new dummytour(travel,photo,url);
                                maketour.child(id).setValue(d);
                            }

                        }
                    });
                }


               //can be passed objects string

            }
        });


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            //
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }
    }
    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        super.onActivityResult(requestcode,resultcode,data);
        Bitmap img=(Bitmap)data.getExtras().get("data");
        try{
            photo.add(Backgroundlocation.l.get(Backgroundlocation.l.size()-1));
            bit.add(img);
        }
        catch(Exception e)
        {}


    }

}