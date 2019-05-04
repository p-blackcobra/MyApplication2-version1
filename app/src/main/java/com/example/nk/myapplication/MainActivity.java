package com.example.nk.myapplication;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        settings = getApplicationContext().getSharedPreferences(Login.MyPREFERENCES, MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hideItem();
        displaySelectedScreen(R.id.nav_home);
    }
    private void hideItem()
    {
        final String phone=settings.getString("Phone", "").toString();
        final String password=settings.getString("Password","").toString();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if(!(phone.isEmpty() && password.isEmpty()))
        {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        /*final String phone=settings.getString("Phone", "").toString();
        final String password=settings.getString("Password","").toString();
        if(!(phone.isEmpty() && password.isEmpty()))
        {
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(true);
        }
        else
        {
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_logout).setVisible(false);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final String password=settings.getString("Password","").toString();
            if(password.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "You Must Login First...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                displaySelectedScreen(R.id.action_settings);
            }
            return true;
        }
        else if(id == R.id.favorites_menu)
        {
            Fragment fragment = new Favourites();
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new Home();
                break;
            case R.id.nav_aboutus:
                fragment = new AboutUs();
                break;
            case R.id.nav_favourite:
                fragment = new Favourites();
                break;
            case R.id.nav_login:
                fragment = new Login();
                break;
            case R.id.action_settings:
                fragment=new Settings();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.nav_logout)
        {
            settings.edit().clear().commit();
            Toast.makeText(getApplicationContext(), "Logout Successfull...", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else {
            displaySelectedScreen(item.getItemId());
        }
        return true;
    }
}