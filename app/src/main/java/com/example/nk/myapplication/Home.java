package com.example.nk.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Home extends Fragment {
    public static String selected_location = "";
    public static String str = "";
    private static final String TAG = "HomeActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mMessListView;
    private MessAdapter mMessAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;

    private Button btn_filter;
    private  Button btn_sortby;
    private  Button select_area_btn;
    private Button search_by_area_btn;

    private ImageButton btn_search;

    private EditText search_keyword;

    public static int veg, service_type;
    public static String area;
    String keywrd;

    AlertDialog alertDialog1;
    AlertDialog alertDialog2;
    CharSequence[] values = { " Mess Rates "," Rating "," Mess Name "};
    CharSequence[] locations = {"All","Shegaon Naka","Panchawati","Gadge Nagar","Kathora Naka","Jamil Colony","Mahendra Colony, new cotton market","Jawahar Stadium","Vilas Nagar","Chaparasi Pura",        "Wadali",
  "Dastur Nagar",
       "Frejarpura","Rukhmini Nagar" ,"Ambapeth Gaurakshan","Jawahar gate","Chaya Nagar" ,"Gauli Pura","Alim Nagar","Gadgareshwar"        ,"Rajapeth "
   ,"Sai nagar"
      ,"Sutgirni","Juni Wasti, Badnera","Navi Wasti, Badnera","Farshi stop","Kanwar nagar","Pravin nagar","Ravi nagar"

 ,"Irwin" ,"Navathe"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        final ImageView favheart;
//        favheart = (ImageView)getView().findViewById(R.id.addtf_icon) ;
//        favheart.setOnClickListener(new View.OnClickListener() {
//            int i=0;
//            public void onClick(View v) {
//                if(i==0) {
//                    favheart.setImageResource(R.drawable.ic_favorite_red_24dp);
//                    i = 1;
//                }
//                else {
//                    i=0;
//                    favheart.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                }
//            }
//        });






        select_area_btn = getView().findViewById(R.id.select_area_button);
        search_by_area_btn = getView().findViewById(R.id.search_by_area);
        select_area_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Area ... ");
                builder.setIcon(R.drawable.ic_location_on_black_24dp);
                builder.setSingleChoiceItems(locations, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                    select_area_btn.setText(locations[item]);
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();

            }
        });
        search_by_area_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
selected_location = select_area_btn.getText().toString();
                Fragment fragment = new showing_list();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

            }
        });
        getActivity().setTitle("Home");
    }


}
