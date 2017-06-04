package com.saurov.android.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.saurov.android.R;
import com.saurov.android.activities.Login;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.activities.MedicationActivity;
import com.saurov.android.database.User;

import java.util.Iterator;

public class SideDrawer {

    public static void showDrawer(final Activity activity) {

        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.home);
        PrimaryDrawerItem medicationItem = new PrimaryDrawerItem().withIdentifier(2).withName("Medication").withIcon(R.drawable.medication);
        PrimaryDrawerItem logOutitem = new PrimaryDrawerItem().withIdentifier(3).withName("Log Out").withIcon(R.drawable.log_out);


        new DrawerBuilder()
                .withActivity(activity)

                .addDrawerItems(
                        homeItem,
                       //contactsItem,
                        //groupsItem,
                        medicationItem,
                        logOutitem

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
                        else if(position == 2)
                        {

                            try
                            {
                                for (Iterator<User> iter = User.findAll(User.class); iter.hasNext(); ) {

                                    Toast.makeText(activity,"Clicked",Toast.LENGTH_LONG).show();
                                    User element = iter.next();

                                    if(element.getIsLoggedIn()==1){

                                        element.setIsLoggedIn(0);

                                        element.save();

                                        Intent i = new Intent(activity,Login.class);

                                        activity.finish();
                                        activity.startActivity(i);

                                        break;
                                    }
                                }

                            }
                            catch (Exception e)
                            {
                                Intent i = new Intent(activity, Login.class);
                                activity.startActivity(i);
                            }
                        }

                        return  true;
                    }
                })
                .build();
    }
}
