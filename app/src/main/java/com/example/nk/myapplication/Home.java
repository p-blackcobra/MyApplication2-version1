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
import android.widget.ImageView;
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
    public static String str = "";
    private static final String TAG = "HomeActivity";
    private ListView mMessListView;
    private MessAdapter mMessAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        final ImageView favheart;
//        favheart = (ImageView)getView().findViewById(R.id.addtf_icon) ;
//        favheart.setOnClickListener(new View.OnClickListener() {
//            int i=0;
//            public void onClick(View v) {
//                if(i==0) {
//                    favheart.setImageResource(R.drawable.ic_favorite_red_24dp);
//                    i = 1;
//                }
//                else {
//                    i=0;
//                    favheart.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                }
//            }
//        });
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
                String messUID= currentMess.getMessUID();
                str=messUID;
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Intent anotherActivityIntent = new Intent(getActivity(), contact.class);
                anotherActivityIntent.putExtra("MESSNAME",currentMess.getMessName());

                startActivity(anotherActivityIntent);


            }
        });
        getActivity().setTitle("Home");
    }
}
