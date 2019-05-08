package com.example.nk.myapplication;

import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentOne extends Fragment {
    private static final String TAG = "FragmentOne";
    private static final String PREF_USER_MOBILE_PHONE = "pref_user_mobile_phone";
    private static final int SMS_PERMISSION_CODE = 0;

    SharedPreferences sharedpreferences;


    private String name = "";
    private int i,j;
    private String contact,phone,mess,address;
    int flag;
    private TextView messNameView;
    private TextView menusTextView;
    private TextView feastTextView;
private ImageView messType;
private ImageView serviceType;
    private TextView guestTiffinChargesTextView;
    private TextView messOwnerTextView;
    private TextView messRateTextView;
    private TextView remarksTextiew;

    private Button btn_fav, view_contact;
    private ArrayList<String> arr,arr1;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    private DatabaseReference mFavDatabaseReference;
    private DatabaseReference mContactDatabaseReference;
    private DatabaseReference mNewFavDatabaseReference;
    private final String DatabaseName = "messdet";
    private final String DatabaseFavrateTableName = "favourate";
    private final String DatabaseContactTableName = "contact";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_fragment_one, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messType = (ImageView)getView().findViewById(R.id.type);
        serviceType = (ImageView)getView().findViewById(R.id.service_type);
        messNameView = (TextView) getView().findViewById(R.id.messName);
        feastTextView = (TextView) getView().findViewById(R.id.feast);
        guestTiffinChargesTextView = (TextView) getView().findViewById(R.id.guestTiffinCharges);
        messOwnerTextView = (TextView) getView().findViewById(R.id.messOwner);
        messRateTextView = (TextView) getView().findViewById(R.id.rates);
        remarksTextiew = (TextView) getView().findViewById(R.id.remarks);
        menusTextView = (TextView) getView().findViewById(R.id.Menus);

        btn_fav = (Button) getView().findViewById(R.id.add_to_fav);
        view_contact = (Button) getView().findViewById(R.id.veiw_contact);

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
                        guestTiffinChargesTextView.setText(messCompleteDetail.getGuestTiffinCharges());
                        messOwnerTextView.setText(messCompleteDetail.getMessOwner());
                        messRateTextView.setText(messCompleteDetail.getMessRate());
                        remarksTextiew.setText(messCompleteDetail.getRemarks());
                        menusTextView.setText(messCompleteDetail.getMenus());
                        contact=messCompleteDetail.getContactNumber();
                        mess=messCompleteDetail.getMessName();
                        address=messCompleteDetail.getAddress();
if(messCompleteDetail.getMessType().equalsIgnoreCase("veg"))
{
    messType.setImageResource(R.drawable.veg);
}
else
{
    messType.setImageResource(R.drawable.both1);
}
if(messCompleteDetail.getService().equalsIgnoreCase("tiffin"))
{
    serviceType.setImageResource(R.drawable.tiffin);
}
else if(messCompleteDetail.getService().equalsIgnoreCase("mess"))
{
    serviceType.setImageResource((R.drawable.messonly));
}
else
{
    serviceType.setImageResource((R.drawable.messandtiffin));
}


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
          //  Toast.makeText(getContext(), "You Must Login First...", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "It's your  favorites mess", Toast.LENGTH_SHORT).show();
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
            view_contact.setOnClickListener(new View.OnClickListener() {
                SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
                String ph = sharedpreferences.getString("Phone", "");
                String uname = sharedpreferences.getString("Name", "");

                @Override
                public void onClick(View view) {
                    if (!hasValidPreConditions()) return;


                    SmsHelper.sendDebugSms(ph, SmsHelper.SMS_CONDITION + "Mess Name:"+mess+"\nContact:"+contact+"\nAddress:"+address);
                    Toast.makeText(getContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
                    SmsHelper.sendDebugSms(contact, SmsHelper.SMS_CONDITION + "Viewed By\nName:"+uname+"\n"+"Contact:"+ph);


                    //Add to database
                    i = 0;
                    arr1 = new ArrayList<String>();
                    mContactDatabaseReference = mFirebaseDatabase.getReference().child(DatabaseContactTableName).child(name);
                    mContactDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            i = 0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                arr1.add(i, (String) ds.getValue());
                            }

                            if (true) {
                                if (!arr1.contains(name)) {
                                    //Toast.makeText(getContext(), "Adding to favorites", Toast.LENGTH_SHORT).show();
                                    arr1.add(i, name);
                                    Collections.sort(arr1);
                                    mContactDatabaseReference.setValue(arr1);

                                }

                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to add value.", error.toException());
                        }
                    });



                }


        });


    }
}


    /**
     * Validates if the app has readSmsPermissions and the mobile phone is valid
     *
     * @return boolean validation value
     */
    private boolean hasValidPreConditions() {
        if (!hasReadSmsPermission()) {
            requestReadAndSendSmsPermission();
            return false;
        }


        return true;
    }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
    }
}
