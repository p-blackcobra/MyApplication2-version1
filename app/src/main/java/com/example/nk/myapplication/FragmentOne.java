package com.example.nk.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentOne extends Fragment {
    private static final String TAG = "FragmentOne";
    private String name = "";
    private TextView messNameView;
    private TextView menusTextView;
    private TextView feastTextView;
    private TextView servicesTextView;
    private TextView guestTiffinChargesTextView;
    private TextView messOwnerTextView;
    private TextView messRateTextView;
    private TextView remarksTextiew;
    private  TextView messTypeTextView;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_fragment_one, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messNameView=(TextView)getView().findViewById(R.id.messName);
        feastTextView=(TextView)getView().findViewById(R.id.feast);
        servicesTextView=(TextView)getView().findViewById(R.id.Services);
        guestTiffinChargesTextView=(TextView)getView().findViewById(R.id.guestTiffinCharges);
        messOwnerTextView=(TextView)getView().findViewById(R.id.messOwner);
        messRateTextView=(TextView)getView().findViewById(R.id.messRate);
        remarksTextiew=(TextView)getView().findViewById(R.id.remarks);
        menusTextView=(TextView)getView().findViewById(R.id.Menus);
        messTypeTextView=(TextView)getView().findViewById(R.id.messType);


        name=Home.str;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");

        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessCompleteDetail messCompleteDetail = ds.getValue(MessCompleteDetail.class);
                    if((messCompleteDetail.getMessName())== name )
                    {
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

//        messNameView.setText(getIntent().getExtras().getString("MESSNAME"));
//        feastTextView.setText(getIntent().getExtras().getString("MESSFEAST"));
//        servicesTextView.setText(getIntent().getExtras().getString("MESSSRV"));
//           guestTiffinChargesTextView.setText(getIntent().getExtras().getString("MESSGTC"));
//        messOwnerTextView.setText(getIntent().getExtras().getString("MESSOWNER"));
//        messRateTextView.setText(getIntent().getExtras().getString("MESSRATE"));
//        remarksTextiew.setText(getIntent().getExtras().getString("MESSRMRK"));
//        menusTextView.setText(getIntent().getExtras().getString("MESSMENUS"));
//        messTypeTextView.setText(getIntent().getExtras().getString("MESSTYPE"));

    }
}