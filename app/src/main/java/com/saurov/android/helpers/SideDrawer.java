package com.saurov.android.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.saurov.android.R;
import com.saurov.android.activities.DoctorActivity;
import com.saurov.android.activities.Login;
import com.saurov.android.activities.MainActivity;
import com.saurov.android.activities.MedicationActivity;
import com.saurov.android.activities.MedicineHistoryActivity;
import com.saurov.android.database.User;

import java.util.Iterator;

public class SideDrawer {


    public static void showDrawer(final Activity activity) {

        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withIdentifier(1).withName("Home").withIcon(R.drawable.home);
        PrimaryDrawerItem medicationItem = new PrimaryDrawerItem().withIdentifier(2).withName("Medications").withIcon(R.drawable.medication);
        PrimaryDrawerItem doctorItem = new PrimaryDrawerItem().withIdentifier(100).withName("Doctors").withIcon(GoogleMaterial.Icon.gmd_folder_person);
        PrimaryDrawerItem historyItem = new PrimaryDrawerItem().withIdentifier(101).withName("History").withIcon(GoogleMaterial.Icon.gmd_cloud_circle);

        //final MySharedPreference mySharedPreference = new MySharedPreference(activity);

        long currentLoggedInUserId = MySharedPreference.getCurrentUserId(activity);

        //PrimaryDrawerItem logOutItem = new PrimaryDrawerItem().withIdentifier(3).withName("Log Out").withIcon(R.drawable.log_out);


        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(activity)
                .addDrawerItems(
                        homeItem,
                        medicationItem,
                        doctorItem,
                        historyItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1) {
                            activity.finish();
                            activity.startActivity(new Intent(activity, MainActivity.class));
                        } else if (drawerItem.getIdentifier() == 2) {
                            activity.finish();
                            activity.startActivity(new Intent(activity, MedicationActivity.class));
                        } else if(drawerItem.getIdentifier() == 100){
                            activity.finish();
                            activity.startActivity(new Intent(activity, DoctorActivity.class));
                        } else if (drawerItem.getIdentifier() == 101) {
                            activity.finish();
                            activity.startActivity(new Intent(activity, MedicineHistoryActivity.class));
                        }
                        return true;
                    }
                });



        if (currentLoggedInUserId!=-1) {

            User user = User.findById(User.class, currentLoggedInUserId);

            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(activity)
                    .addProfiles(
                            new ProfileDrawerItem().withName(user.getUserName()).withEmail(user.getEmail())
                                    .withIcon(R.drawable.account_icon_default),
                            new ProfileSettingDrawerItem().withIdentifier(3).withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings),
                            new ProfileSettingDrawerItem().withIdentifier(4).withName("Log Out").withIcon(R.drawable.log_out)
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            if (profile != null && profile instanceof IDrawerItem) {

                                if (profile.getIdentifier() == 3)
                                    Toast.makeText(activity, "Will Implement Later!!", Toast.LENGTH_SHORT).show();

                                if (profile.getIdentifier() == 4) {

                                    try {
                                        for (Iterator<User> iter = User.findAll(User.class); iter.hasNext(); ) {

                                            //Toast.makeText(activity,"Clicked",Toast.LENGTH_LONG).show();
                                            User element = iter.next();

                                            if (element.getIsLoggedIn() == 1) {

                                                element.setIsLoggedIn(0);

                                                MySharedPreference.setCurrentUserId(activity,-1);

                                                //MainActivity.loggedInUserId = -1;

                                                element.save();

                                                Intent i = new Intent(activity, Login.class);

                                                activity.finish();
                                                activity.startActivity(i);

                                                break;
                                            }
                                        }

                                    } catch (Exception e) {
                                        Intent i = new Intent(activity, Login.class);
                                        activity.startActivity(i);
                                    }
                                }
                            }

                            return true;
                        }
                    })
                    .withHeaderBackground(R.drawable.account_header_image)
                    .build();

            builder.withAccountHeader(headerResult)
                    .build();
        } else {

            builder.build();
        }
    }
}
