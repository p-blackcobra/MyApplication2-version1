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
import android.support.v4.app.FragmentTransaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.nk.myapplication.Common.Common;
import com.example.nk.myapplication.Model.User;

public class SignUp extends Fragment {

    EditText edtPhone, edtName, edtPassword, edtEmail;
    Button btnSignUp;
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
        edtName = getView().findViewById(R.id.edtNewUserName);
        edtPassword = getView().findViewById(R.id.edtNewPassword);
        edtPhone = getView().findViewById(R.id.edtNewPhone);
        edtEmail = getView().findViewById(R.id.edtNewEmail);
        btnSignUp = getView().findViewById(R.id.btnSignUp);

        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedToInternet(getContext())) {
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
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString(), edtEmail.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Password, user.getPassword());
                                editor.putString(Phone, user.getPhone());
                                editor.commit();
                                Toast.makeText(getContext(), "SignUp Successfull...", Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Internet Connection Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
