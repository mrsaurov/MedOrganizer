package com.saurov.android.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.saurov.android.R;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.activities.MedicationActivity;
/**
 * Created by Nowfel Mashnoor on 3/21/2017.
 */

public class SideDrawer {

    public static void showDrawer(final Activity activity) {

        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem contactsItem = new PrimaryDrawerItem().withIdentifier(2).withName("Contacts");
        PrimaryDrawerItem groupsItem = new PrimaryDrawerItem().withIdentifier(3).withName("Groups");
        PrimaryDrawerItem medicationItem = new PrimaryDrawerItem().withIdentifier(4).withName("Medication");



        new DrawerBuilder()
                .withActivity(activity)

                .addDrawerItems(
                        homeItem,
                       contactsItem,
                        groupsItem,
                        medicationItem

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position==0)
                        {
                            activity.finish();
                            activity.startActivity(new Intent(activity, MainActivity.class));
                        }
                        else if(position==1)
                        {
                            activity.finish();
                            activity.startActivity(new Intent(activity, MainActivity.class));
                        }
                        else if(position==3)
                        {
                            activity.finish();
                            activity.startActivity(new Intent(activity,MedicationActivity.class));
                        }
                        return  true;
                    }
                })
                .build();
    }



}
