package com.example.nk.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.nk.myapplication.Common.Common;
import com.example.nk.myapplication.Model.User;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
public class Login extends Fragment {
    private static final String TAG = "SignIn";
    private static final int SMS_PERMISSION_CODE = 0;
    EditText edtPhone,edtOTP;
    Button btnSignIn,btnOTP;
    TextView txtsignUp,txtForgot;
    String match,otp;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "Phone";
    SharedPreferences sharedpreferences;
    private AwesomeValidation awesomeValidation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_login, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtPhone = (EditText) getView().findViewById(R.id.edtPhone);
        edtOTP=(EditText)getView().findViewById(R.id.edtOTP);
        btnSignIn = (Button) getView().findViewById(R.id.btnSignIn);
        btnOTP=(Button)getView().findViewById(R.id.btnOTP);
        txtsignUp = (TextView) getView().findViewById(R.id.txtSignUp);
       // txtForgot = (TextView) getView().findViewById(R.id.txtForgot);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(getActivity(), R.id.edtPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        /*
         * Check if we successfully logged in before.
         * If we did, redirect to home page
         */
        final SharedPreferences login = getContext().getSharedPreferences(Login.MyPREFERENCES,getContext().MODE_PRIVATE);
        final String phone=login.getString("Phone", "").toString();
        if(phone.isEmpty()) {

        }
        else
        {
            // Init Firebase
            if (Common.isConnectedToInternet(getContext())) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if user exist or not
                        if (dataSnapshot.child(phone.toString()).exists()) {
                            Fragment home = new Home();
                            getFragmentManager().beginTransaction().replace(R.id.content_frame, home).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(getContext(), "Internet Connection Failed...", Toast.LENGTH_SHORT).show();
                return;
            }
        }
       /* txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),ForgotPassword.class);
                startActivity(intent);
            }
        });*/

        txtsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment signUp = new SignUp();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, signUp).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
        btnOTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!hasValidPreConditions()) return;
                int randomPin   =(int)(Math.random()*9000)+1000;
                otp  =String.valueOf(randomPin);

                if(awesomeValidation.validate()) {
                    SmsHelper.sendDebugSms(edtPhone.getText().toString(), SmsHelper.SMS_CONDITION + "Verification Code is " + otp);
                    Toast.makeText(getContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
                    match = otp;
                    btnOTP.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.VISIBLE);
                }

            }


        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awesomeValidation.addValidation(getActivity(), R.id.edtOTP, match,R.string.OTPError);
                if(awesomeValidation.validate()) {
                    if (Common.isConnectedToInternet(getContext())) {

                        final ProgressDialog mDialog = new ProgressDialog(getContext());
                        mDialog.setMessage("Please wait...");
                        if (awesomeValidation.validate()) {
                            mDialog.show();
                            table_user.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    //check if user exist or not
                                    if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                        mDialog.dismiss();
                                        //get user information
                                        User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                        user.setPhone(edtPhone.getText().toString()); // set Phone
                                        if (user.getPhone().equals(edtPhone.getText().toString())) {
                                            SharedPreferences.Editor editor = login.edit();
                                            editor.putString(Phone, user.getPhone());
                                            editor.putString("Name", user.getName());
                                            editor.commit();
                                            Toast.makeText(getContext(), "Login Successfull...", Toast.LENGTH_SHORT).show();
                                            Common.currentUser = user;
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            startActivity(intent);
                                            //Fragment home = new Home();
                                            //getFragmentManager().beginTransaction().replace(R.id.content_frame, home).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                                        } else {
                                            Toast.makeText(getContext(), "SignIn failed...", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(getContext(), "User not exist...", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Internet Connection Failed...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Login");
    }
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