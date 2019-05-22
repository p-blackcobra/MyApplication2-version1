package com.example.nk.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.nk.myapplication.Common.Common;
import com.example.nk.myapplication.Model.User;
import android.content.Intent;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class  SignUp extends Fragment {

    private static final String TAG = "SignUp";
    private static final String PREF_USER_MOBILE_PHONE = "pref_user_mobile_phone";
    private static final int SMS_PERMISSION_CODE = 0;

    EditText edtPhone, edtName,edtOTP;
    Button btnSignUp,btnOTP;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    String phoneNumber, otp;
    String match;
    //FirebaseAuth auth;
    //PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    //private String verificationCode;
    public static final String Phone = "Phone";
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_sign_up, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtName = (EditText)getView().findViewById(R.id.edtNewUserName);
        edtPhone = (EditText)getView().findViewById(R.id.edtNewPhone);
        edtOTP = getView().findViewById(R.id.edtOTP);
        btnSignUp = getView().findViewById(R.id.btnSignUp);
        btnOTP = getView().findViewById(R.id.btnOTP);
        //StartFirebaseLogin();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(getActivity(), R.id.edtNewUserName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.edtNewPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
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
                        btnSignUp.setVisibility(View.VISIBLE);
                    }

                }


        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awesomeValidation.addValidation(getActivity(), R.id.edtOTP, match,R.string.OTPError);
                if(awesomeValidation.validate())
                {
                if (Common.isConnectedToInternet(getContext())) {
                    //otp=edtCode.getText().toString();
                    //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                    //SigninWithPhone(credential);
                    final ProgressDialog mDialog = new ProgressDialog(getContext());
                    mDialog.setMessage("Please wait...");
                    mDialog.show();
                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // check if user already exists
                                if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                    mDialog.dismiss();
                                    Toast.makeText(getContext(), "User already registered...", Toast.LENGTH_SHORT);
                                } else {
                                    mDialog.dismiss();
                                    User user = new User(edtName.getText().toString());
                                    table_user.child(edtPhone.getText().toString()).setValue(user);
                                    SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Phone, user.getPhone());
                                    editor.commit();
                                    Toast.makeText(getContext(), "SignUp Successfull...", Toast.LENGTH_SHORT);
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                }
                else {
                    Toast.makeText(getContext(), "Internet Connection Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }});

        getActivity().setTitle("Register User");
    }
   /* private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),"Register Successfull...",Toast.LENGTH_SHORT).show();
                            } else {
                            Toast.makeText(getContext(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
    /*private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getContext(),"verification completed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getContext(),"verification fialed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(getContext(),"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }*/
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
