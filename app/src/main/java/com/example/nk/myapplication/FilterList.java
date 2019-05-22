package com.example.nk.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


public class FilterList extends Fragment {

    private TextView cancelButton;
    private TextView applyButton;
   private Switch s;
private Spinner spinner_area;
   private Spinner spinner_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_dailog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancelButton =  getView().findViewById(R.id.filter_cancel);
        applyButton =  getView().findViewById(R.id.filter_apply);
        s = getView().findViewById(R.id.switch1);
        spinner_service =getView().findViewById(R.id.spinner_service);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (s.isChecked()) {
                    Home.veg = 1; // means only veg category to be displayed
                } else {
                    Home.veg = 0; // no filter on the type of mess display all the mess
                }


                if (spinner_area.getSelectedItem().toString().equalsIgnoreCase("all")) {
                    //dispaly all
                } else {
                    Home.area = spinner_area.getSelectedItem().toString();
                }
                Home.service_type = spinner_service.getSelectedItemPosition();
                getFragmentManager().popBackStack();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }
    }
