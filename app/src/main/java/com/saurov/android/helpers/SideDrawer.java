package com.saurov.android.helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.saurov.android.R;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.activities.MedicationActivity;

public class SideDrawer {

    public static void showDrawer(final Activity activity) {

        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.home);
        //PrimaryDrawerItem contactsItem = new PrimaryDrawerItem().withIdentifier(2).withName("Contacts");
        //PrimaryDrawerItem groupsItem = new PrimaryDrawerItem().withIdentifier(3).withName("Groups");
        PrimaryDrawerItem medicationItem = new PrimaryDrawerItem().withIdentifier(2).withName("Medication").withIcon(R.drawable.medication);



        new DrawerBuilder()
                .withActivity(activity)

                .addDrawerItems(
                        homeItem,
                       //contactsItem,
                        //groupsItem,
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
                            activity.startActivity(new Intent(activity,MedicationActivity.class));
                        }
                        return  true;
                    }
                })
                .build();
    }
}
