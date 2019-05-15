package com.example.nk.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView LogoHeadr ;
    TextView UserNameHeadr ;
    TextView EmailHeadr ;
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
setHeaderFields();
        navigationView.setNavigationItemSelectedListener(this);
        hideItem();
        displaySelectedScreen(R.id.nav_home);
        View defView = findViewById(android.R.id.content);
        if(isNetworkAvailable()== false)
        {
            Snackbar snackbar = Snackbar.make( defView,"No Internet Connrction... ",Snackbar.LENGTH_LONG).setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            //View sbView = snackbar.getView();
            //sbView.setBackgroundColor(getResources().getColor(R.color.customRED));
                   snackbar.setActionTextColor(getResources().getColor(android.R.color.white ));
                    snackbar.show();
        }
    }
    private void hideItem()
    {
        final String phone=settings.getString("Phone", "").toString();
        final String password=settings.getString("Password","").toString();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu nav_Menu = navigationView.getMenu();
        if(!(phone.isEmpty() && password.isEmpty()))
        {
//            LogoHeadr =(TextView)findViewById(R.id.imageTexViewView);
//            UserNameHeadr =(TextView)findViewById(R.id.userNameTextView);
//            EmailHeadr =(TextView)findViewById(R.id.EmailTextView);
//            char n = (settings.getString("Name","").toString()).charAt(0);
//            LogoHeadr.setText(n);
//            UserNameHeadr.setText(settings.getString("Name","").toString());
//            EmailHeadr.setText(settings.getString("EmailId","").toString());
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_favourite).setVisible(true);
//            nav_Menu.findItem(R.id.favorites_menu).setVisible(true);
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to exit this app ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final String phone=settings.getString("Phone", "").toString();
        final String password=settings.getString("Password","").toString();
        if(!(phone.isEmpty() && password.isEmpty())) {
            menu.findItem(R.id.favorites_menu).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
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
        }*/
        if(id == R.id.favorites_menu)
        {
            Fragment fragment = new Favourites();

            if((settings.getString("Phone","").toString().length())<1 )
            {
                Toast.makeText(getApplicationContext(), "You Must Login First...", Toast.LENGTH_SHORT).show();
            }
            else if (fragment != null) {
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
            /*case R.id.action_settings:
                fragment=new Settings();
                break;*/
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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void setHeaderFields()
    {

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView logoTextView = (TextView)headerView.findViewById(R.id.imageTexViewView);
        TextView userNameTextView = (TextView)headerView.findViewById(R.id.userNameTextView);
        TextView userMailTextView = (TextView)headerView.findViewById(R.id.EmailTextView);
        if((settings.getString("Name","")).length()>0) {

            //logoTextView.setText((settings.getString("Name", "")).charAt(0));
            userNameTextView.setText(settings.getString("Name", ""));
            userMailTextView.setText(settings.getString("EmailId", ""));
        }
        else
        {
           /* logoTextView.setText("E");
            userNameTextView.setText("Name");
            userMailTextView.setText("EmailId");*/
        }
    }
}