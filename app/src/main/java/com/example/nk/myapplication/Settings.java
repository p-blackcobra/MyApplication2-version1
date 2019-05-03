package com.example.nk.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class Settings extends Fragment {
    EditText edtPwd, edtNewPwd;
    Button btnChangePwd;
    SharedPreferences login;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtPwd = (EditText) getView().findViewById(R.id.edtPwd);
        edtNewPwd = (EditText) getView().findViewById(R.id.edtNewPwd);
        btnChangePwd = (Button) getView().findViewById(R.id.btnChangePwd);
        login = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
        final String password=login.getString("Password","").toString();
        final String phone=login.getString("Phone","").toString();
        final String name=login.getString("Name","").toString();
        final String email=login.getString("EmailId","").toString();
        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(getContext());
                    mDialog.setMessage("Please wait...");
                    mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                mDialog.dismiss();
                            User user = dataSnapshot.child(phone).getValue(User.class);
                            if (password.equals(edtPwd.getText().toString())) {
                                   user.setPassword(edtNewPwd.getText().toString());
                                   user.setEmailId(email);
                                   user.setName(name);
                                   table_user.child(phone).setValue(user);
                                   SharedPreferences.Editor editor = login.edit();
                                   editor.putString("Password", user.getPassword());
                                   editor.putString("Phone", user.getPhone());
                                   editor.commit();
                                   Toast.makeText(getContext(), "Password Changed Successfully...", Toast.LENGTH_SHORT).show();
                                   Fragment home = new Home();
                                    getFragmentManager().beginTransaction().replace(R.id.content_frame, home).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                                } else {
                                    Toast.makeText(getContext(), "Wrong Password Entered...", Toast.LENGTH_SHORT).show();
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
        getActivity().setTitle("Change Password");
    }
}
