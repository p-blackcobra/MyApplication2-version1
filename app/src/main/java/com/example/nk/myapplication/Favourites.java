package com.example.nk.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends Fragment {
    public String arr[]=new String[12];
    public int i=0;
    public int j = 0 ;
    private static final String TAG = "Favourites";
    private ListView mMessListView;
    private MessAdapter mMessAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    private DatabaseReference mFavoritesReference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_favourites, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
//String userMob = Login.UserNo;
        String userMob ;
        userMob = "abcd9876";
        SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
        userMob = sharedpreferences.getString("Phone", "");
i=0;
if(userMob.equals("abcd9876")) { }
else {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFavoritesReference = mFirebaseDatabase.getReference().child("favourate").child(Login.UserNo).child("messID");
        mFavoritesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    arr[i] = ds.getValue(String.class);
                    Log.d(TAG, arr[i]);
                    Log.d(TAG, String.valueOf(i));
                    i++;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



j=0;
        mMessListView = (ListView) getView().findViewById(R.id.favmessListView);
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");
  // Initialize message ListView and its adapter
        List<MessAbstract> mess = new ArrayList<>();
        mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
        mMessListView.setAdapter(mMessAdapter);

        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey().equals(arr[j])) {
                        MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                        mMessAdapter.add(messAbstract);
                    }
                    j++;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
}
        getActivity().setTitle("Favourites");






        }
}
