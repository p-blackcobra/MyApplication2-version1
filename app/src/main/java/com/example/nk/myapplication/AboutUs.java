package com.example.nk.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_aboutus, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img = getView().findViewById(R.id.image);
        img.setImageResource(R.drawable.logon);
        TextView tv1 = getView().findViewById(R.id.textView2);
        tv1.setText(R.string.parai);
        TextView tv2 = getView().findViewById(R.id.textView4);
        tv2.setText(R.string.paraii);
        TextView tv3 = getView().findViewById(R.id.textView6);
        tv3.setText(R.string.nameofcfs);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("About Us");
    }
}
