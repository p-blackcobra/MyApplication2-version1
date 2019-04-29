package com.example.nk.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Home extends Fragment {
    private static final String TAG = "HomeActivity";
    private ListView mMessListView;
    private MessAdapter mMessAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    private ChildEventListener childEventListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMessListView = (ListView) getView().findViewById(R.id.messageListView);
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");



        // Initialize message ListView and its adapter
        List<MessAbstract> mess = new ArrayList<>();
        mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
        mMessListView.setAdapter(mMessAdapter);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                    mMessAdapter.add(messAbstract);
                    Log.d("TAG", messAbstract.getMessName());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mMessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                MessAbstract currentMess = mMessAdapter.getItem(position);
                String messName= currentMess.getMessName();
                String messOwner= currentMess.getMessOwner();
                String messAddress= currentMess.getAddress();
                String messRate= currentMess.getMessRate();
                String messType= currentMess.getMessType();
                String contactNumber= currentMess.getContactNumber();
                String feast= currentMess.getFeast();
                String guestTiffinCharges= currentMess.getGuestTiffinCharges();
                String menus= currentMess.getMenus();
                String remarks= currentMess.getRemarks();
                String service= currentMess.getService();
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Intent anotherActivityIntent = new Intent(getActivity(), contact.class);
                anotherActivityIntent.putExtra("MESSNAME",messName);
                anotherActivityIntent.putExtra("MESSOWNER",messOwner);
                anotherActivityIntent.putExtra("MESSADD",messAddress);
                anotherActivityIntent.putExtra("MESSRATE",messRate);
                anotherActivityIntent.putExtra("MESSTYPE",messType);
                anotherActivityIntent.putExtra("MESSCNO",contactNumber);
                anotherActivityIntent.putExtra("MESSFEAST",feast);
                anotherActivityIntent.putExtra("MESSGTC",guestTiffinCharges);
                anotherActivityIntent.putExtra("MESSMENUS",menus);
                anotherActivityIntent.putExtra("MESSRMRK",remarks);
                anotherActivityIntent.putExtra("MESSSRV",service);

                startActivity(anotherActivityIntent);

                // Create a new intent to view the earthquake URI


                // Send the intent to launch a new activity

            }
        });
//if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//    NotificationChannel channel = new NotificationChannel("MyNotifications","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
//    NotificationManager manager = getSystemService(NotificationManager.class);
//    manager.createNotificationChannel(channel);
//        }
//
//        FirebaseMessaging.getInstance().subscribeToTopic("general")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "SuccessFull";
//                        if (!task.isSuccessful()) {
//                            msg ="Failed";
//                        }
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");
    }
}
