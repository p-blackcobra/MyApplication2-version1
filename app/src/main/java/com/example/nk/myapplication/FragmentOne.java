package com.example.nk.myapplication;

import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentOne extends Fragment {
    private static final String TAG = "FragmentOne";
    private String name = "";
    private int i;
    int flag;
    private TextView messNameView;
    private TextView menusTextView;
    private TextView feastTextView;
    private TextView servicesTextView;
    private TextView guestTiffinChargesTextView;
    private TextView messOwnerTextView;
    private TextView messRateTextView;
    private TextView remarksTextiew;
    private TextView messTypeTextView;

    private Button btn_fav;
    private ArrayList<String> arr;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    private DatabaseReference mFavDatabaseReference;
    private DatabaseReference mNewFavDatabaseReference;
    private final String DatabaseName = "messdet";
    private final String DatabaseFavrateTableName = "favourate" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_fragment_one, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messNameView = (TextView) getView().findViewById(R.id.messName);
        feastTextView = (TextView) getView().findViewById(R.id.feast);
        servicesTextView = (TextView) getView().findViewById(R.id.Services);
        guestTiffinChargesTextView = (TextView) getView().findViewById(R.id.guestTiffinCharges);
        messOwnerTextView = (TextView) getView().findViewById(R.id.messOwner);
        messRateTextView = (TextView) getView().findViewById(R.id.messRate);
        remarksTextiew = (TextView) getView().findViewById(R.id.remarks);
        menusTextView = (TextView) getView().findViewById(R.id.Menus);
        messTypeTextView = (TextView) getView().findViewById(R.id.messType);

        btn_fav = (Button) getView().findViewById(R.id.add_to_fav);

        name = Home.str;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child(DatabaseName);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessCompleteDetail messCompleteDetail = ds.getValue(MessCompleteDetail.class);
                    if ((messCompleteDetail.getMessUID()) == name) {
                        messNameView.setText(messCompleteDetail.getMessName());
                        feastTextView.setText(messCompleteDetail.getFeast());
                        servicesTextView.setText(messCompleteDetail.getService());
                        guestTiffinChargesTextView.setText(messCompleteDetail.getGuestTiffinCharges());
                        messOwnerTextView.setText(messCompleteDetail.getMessOwner());
                        messRateTextView.setText(messCompleteDetail.getMessRate());
                        remarksTextiew.setText(messCompleteDetail.getRemarks());
                        menusTextView.setText(messCompleteDetail.getMenus());
                        messTypeTextView.setText(messCompleteDetail.getMessType());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
        String ph = sharedpreferences.getString("Phone", "");
        if (ph.length() < 1) {
            Toast.makeText(getContext(), "You Must Login First...", Toast.LENGTH_SHORT).show();
            //Fragment login = new Login();
            //getFragmentManager().beginTransaction().replace(R.id.content_frame, login).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        } else {
            i = 0;
            arr = new ArrayList<String>();
            mFavDatabaseReference = mFirebaseDatabase.getReference().child(DatabaseFavrateTableName).child(ph).child("messID");
            mFavDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        arr.add(i, (String) ds.getValue());
                    }
                    if (!arr.isEmpty()) {
                        if (arr.contains(name))
                        {
                           // Toast.makeText(getActivity(), "It's your  favorites mess", Toast.LENGTH_SHORT).show();
                            btn_fav.setEnabled(false);
                        }
                    }
                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            btn_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
                    String ph = sharedpreferences.getString("Phone", "");
                    if (ph.length() < 1) {
                        Toast.makeText(getContext(), "You Must Login First...", Toast.LENGTH_SHORT).show();
                        //Fragment login = new Login();
                        //getFragmentManager().beginTransaction().replace(R.id.content_frame, login).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

                    } else {

                        i = 0;
                        arr = new ArrayList<String>();
                        mFavDatabaseReference = mFirebaseDatabase.getReference().child(DatabaseFavrateTableName).child(ph).child("messID");
                        mFavDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                i = 0;
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    arr.add(i, (String) ds.getValue());
                                }

                                if (true) {
                                    if (!arr.contains(name)) {
                                        Toast.makeText(getContext(), "Adding to favorites", Toast.LENGTH_SHORT).show();
                                        arr.add(i, name);
                                        Collections.sort(arr);
                                        mFavDatabaseReference.setValue(arr);

                                    }

                                }

                            }


                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w(TAG, "Failed to read value.", error.toException());
                            }
                        });
                    }


                }
            });

        }
    }
}