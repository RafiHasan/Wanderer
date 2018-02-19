package com.example.wonderer.wonderer.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.wonderer.wonderer.Homedir.Homelist;
import com.example.wonderer.wonderer.Profiledir.ProfileActivity;
import com.example.wonderer.wonderer.R;
import com.example.wonderer.wonderer.loginregister.Login;
import com.example.wonderer.wonderer.plandir.TravelPlaceList;

import java.lang.reflect.Field;

/**
 * Created by Rafi on 2/13/2018.
 */

public class Bottombarnav extends Activity {
public static int x=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // setContentView(R.layout.layoutbottombar);
        BottomNavigationView bottomNavigationView;
     if(x==1) {
         setContentView(R.layout.social_main);
         bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
         Menu menu = bottomNavigationView.getMenu();
         menu.findItem(R.id.ic_arrow).setChecked(true);


         BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
         try {
             Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
             shiftingMode.setAccessible(true);
             shiftingMode.setBoolean(menuView, false);
             shiftingMode.setAccessible(false);
             for (int i = 0; i < menuView.getChildCount(); i++) {
                 BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                 item.setShiftingMode(false);
                 // set once again checked value, so view will be updated
                 item.setChecked(item.getItemData().isChecked());
             }
         } catch (NoSuchFieldException e) {

         } catch (IllegalAccessException e) {

         }



     }
        else if(x==2) {
            setContentView(R.layout.travellistplace_main);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            Menu menu = bottomNavigationView.getMenu();
         bottomNavigationView.setAnimationCacheEnabled(false);

            menu.findItem(R.id.ic_android).setChecked(true);


         BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
         try {
             Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
             shiftingMode.setAccessible(true);
             shiftingMode.setBoolean(menuView, false);
             shiftingMode.setAccessible(false);
             for (int i = 0; i < menuView.getChildCount(); i++) {
                 BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                 item.setShiftingMode(false);
                 // set once again checked value, so view will be updated
                 item.setChecked(item.getItemData().isChecked());
             }
         } catch (NoSuchFieldException e) {

         } catch (IllegalAccessException e) {

         }

        }


        else if(x==3){
         setContentView(R.layout.travellistplace_main);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            Menu menu = bottomNavigationView.getMenu();
            bottomNavigationView.setAnimationCacheEnabled(false);

            menu.findItem(R.id.ic_books).setChecked(true);


            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {

            }

        }

     else if(x==4) {
         setContentView(R.layout.activity_profile_main);
         bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
         Menu menu = bottomNavigationView.getMenu();
         bottomNavigationView.setAnimationCacheEnabled(false);

         menu.findItem(R.id.ic_backup).setChecked(true);


         BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
         try {
             Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
             shiftingMode.setAccessible(true);
             shiftingMode.setBoolean(menuView, false);
             shiftingMode.setAccessible(false);
             for (int i = 0; i < menuView.getChildCount(); i++) {
                 BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                 item.setShiftingMode(false);
                 // set once again checked value, so view will be updated
                 item.setChecked(item.getItemData().isChecked());
             }
         } catch (NoSuchFieldException e) {

         } catch (IllegalAccessException e) {

         }

     }
        else  {
            setContentView(R.layout.travellistplace_main);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            Menu menu = bottomNavigationView.getMenu();
            bottomNavigationView.setAnimationCacheEnabled(false);

            menu.findItem(R.id.ic_android).setChecked(true);


            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {

            }

        }









        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(getApplicationContext(), Login.class);
                        Bottombarnav.x=1;
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(getApplicationContext(), Homelist.class);
                        Bottombarnav.x=2;
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(getApplicationContext(), TravelPlaceList.class);
                        Bottombarnav.x=3;
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Login.mAuth.signOut();
                        Intent intent3 = new Intent(getApplicationContext(), Login.class);
                        Bottombarnav.x=1;
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        ProfileActivity.showprofile=Login.userprofile;
                        ProfileActivity.uid=Login.userid;
                        Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                        Bottombarnav.x=4;
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });


    }


}
