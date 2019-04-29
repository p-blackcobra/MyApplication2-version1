package com.example.nk.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.nk.myapplication.Common.Common;
import com.example.nk.myapplication.Model.User;
public class Login extends Fragment {
    EditText edtPhone, edtPassword;
    Button btnSignIn;
    TextView txtsignUp;

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
        edtPassword = (EditText) getView().findViewById(R.id.edtPassword);
        edtPhone = (EditText) getView().findViewById(R.id.edtPhone);
        btnSignIn = (Button) getView().findViewById(R.id.btnSignIn);
        txtsignUp = (TextView) getView().findViewById(R.id.txtSignUp);
        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        txtsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(getContext(),SignUp.class);
                startActivity(signUp);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(getContext());
                    mDialog.setMessage("Please wait...");
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
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Toast.makeText(getContext(), "Login Successfull...", Toast.LENGTH_SHORT).show();
                                    Common.currentUser = user;
                                    Fragment home = new Home();
                                    getFragmentManager().beginTransaction().replace(R.id.content_frame, home).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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
                } else {
                    Toast.makeText(getContext(), "Internet Connection Failed...", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Login");
    }
}
