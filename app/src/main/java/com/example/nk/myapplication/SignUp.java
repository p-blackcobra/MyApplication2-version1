package com.example.nk.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
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

    EditText edtPhone, edtName, edtPassword, edtEmail;
    Button btnSignUp;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    String phoneNumber, otp;
    //FirebaseAuth auth;
    //PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    //private String verificationCode;
    public static final String Password = "Password";
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
        edtPassword = (EditText)getView().findViewById(R.id.edtNewPassword);
        edtPhone = (EditText)getView().findViewById(R.id.edtNewPhone);
        edtEmail = (EditText)getView().findViewById(R.id.edtNewEmail);
        //edtCode = getView().findViewById(R.id.edtCode);
        btnSignUp = getView().findViewById(R.id.btnSignUp);
        //btnOTP = getView().findViewById(R.id.btnOTP);
        //StartFirebaseLogin();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(getActivity(), R.id.edtNewUserName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.edtNewEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(getActivity(), R.id.edtNewPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(getActivity(), R.id.edtNewPassword, regexPassword, R.string.passworderror);
        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        /*btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=edtPhone.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,                     // Phone number to verify
                        60,                           // Timeout duration
                        TimeUnit.SECONDS,                // Unit of timeout
                        (Executor)getActivity(),        // Activity (for callback binding)
                        mCallback);                      // OnVerificationStateChangedCallbacks
            }
        });*/

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInternet(getContext())) {
                    //otp=edtCode.getText().toString();
                    //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                    //SigninWithPhone(credential);
                    final ProgressDialog mDialog = new ProgressDialog(getContext());
                    mDialog.setMessage("Please wait...");
                    if (awesomeValidation.validate()) {
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
                                    User user = new User(edtName.getText().toString(), edtPassword.getText().toString(), edtEmail.getText().toString());
                                    table_user.child(edtPhone.getText().toString()).setValue(user);
                                    SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Password, user.getPassword());
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
                }
                else {
                    Toast.makeText(getContext(), "Internet Connection Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
}
