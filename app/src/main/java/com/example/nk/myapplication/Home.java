package com.example.nk.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nk.myapplication.Model.Retrieve_loc_from_firebase;
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
    public static String str = "";
    private static final String TAG = "HomeActivity";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView mMessListView;
    private MessAdapter mMessAdapter;
    private FirebaseDatabase mFirebaseDatabase,db;
    private DatabaseReference mMessDatabaseReference,dbref;

    private Button btn_filter;
    private  Button btn_sortby;
    private ImageButton btn_search;

    private EditText search_keyword;

    int veg, service_type;
    String area;
    String keywrd;

    AlertDialog alertDialog1;
    CharSequence[] values = { " Mess Rates "," Rating "," Mess Name "};

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

        swipeRefreshLayout =(SwipeRefreshLayout)getView().findViewById(R.id.pullToRefresh);
         loadData();
         btn_filter = (Button)getView().findViewById(R.id.filters);
         btn_sortby = (Button)getView().findViewById(R.id.sortby);
        btn_search = (ImageButton) getView().findViewById(R.id.search_btn);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_keyword = (EditText)getView().findViewById(R.id.search_keyword);
                keywrd = search_keyword.getText().toString();
                if(keywrd.length()>1) {
                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");
                 //  mMessDatabaseReference.orderByChild("area").startAt(keywrd).endAt(keywrd+"\uf8ff");
                    // Initialize message ListView and its adapter
                    final List<MessAbstract> mess = new ArrayList<>();
                    mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
                    mMessListView.setAdapter(mMessAdapter);
                    mMessDatabaseReference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mMessAdapter.clear();
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                                String are=messAbstract.getArea();
                                if(keywrd.regionMatches(true,0,messAbstract.getArea(),0,keywrd.length()) || keywrd.regionMatches(true,0,messAbstract.getMessName(),0,keywrd.length()) )
                                {
                                    Log.w(TAG,messAbstract.getMessUID()+"    " + messAbstract.getArea());
                                    mMessAdapter.add(messAbstract);
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
        btn_sortby.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 CreateAlertDialogWithRadioButtonGroup() ;
             }
         });
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.filter_dailog);
                dialog.setTitle("Filter");
               TextView cancelButton = (TextView) dialog.findViewById(R.id.filter_cancel);
                TextView applyButton = (TextView) dialog.findViewById(R.id.filter_apply);

                // if button is clicked, close the custom dialog
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Switch s = (Switch)dialog.findViewById(R.id.switch1);
                       if(s.isChecked())
                       {
                           int i=0;
                           while(i<mMessAdapter.getCount())
                           {
                               MessAbstract temp = mMessAdapter.getItem(i);
                             if(! temp.getMessType().equalsIgnoreCase("Veg"))
                               {
                                   Log.w(TAG,"Removed"+temp.getMessName() + "    "+temp.getMessUID());
                                   mMessAdapter.remove(temp);
                               }
                               else
                             {
                               i++;
                           }}
                       }
                       Spinner spinner_area = (Spinner)dialog.findViewById(R.id.spinner_area);
                       //dbref=FirebaseDatabase.getInstance().getReference("Location");
                       //Retrieve_loc_from_firebase helper=new Retrieve_loc_from_firebase(dbref);
                       //spinner_area.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,helper.retrieve()));
                       Spinner spinner_service =(Spinner)dialog.findViewById(R.id.spinner_service);
                       area  = spinner_area.getSelectedItem().toString();
                       if(! area.equalsIgnoreCase("All"))
                       {
                           int i=0;
                           while(i<mMessAdapter.getCount())
                           {
                               MessAbstract temp = mMessAdapter.getItem(i);
                               if(! temp.getArea().equalsIgnoreCase(area))
                               {
                                   Log.w(TAG,"Removed"+temp.getMessName() + "    "+temp.getMessUID());
                                   mMessAdapter.remove(temp);
                               }
                               else
                               {
                                   i++;
                               }}
                       }
                       service_type = spinner_service.getSelectedItemPosition();
                        Log.w(TAG,"selected item id" + service_type);
                       if(service_type==1)
                       {
                           int i=0;
                           while(i<mMessAdapter.getCount())
                           {
                               MessAbstract temp = mMessAdapter.getItem(i);
                               if(temp.getService().equalsIgnoreCase("Mess"))
                               {
                                   Log.w(TAG,"Removed"+temp.getMessName()+" "+temp.getService()+ "    "+temp.getMessUID());
                                   mMessAdapter.remove(temp);
                               }
                               else
                               {
                                   i++;
                               }}
                       }
                       else if (service_type==2)
                       {
                           int i=0;
                           while(i<mMessAdapter.getCount())
                           {
                               MessAbstract temp = mMessAdapter.getItem(i);
                               if( temp.getService().equalsIgnoreCase("Tiffin"))
                               {
                                   Log.w(TAG,"Removed"+temp.getMessName()+" "+temp.getService()+ "    "+temp.getMessUID());
                                   mMessAdapter.remove(temp);
                               }
                               else
                               {
                                   i++;
                               }}
                       }
                       dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        mMessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                // Find the current earthquake that was clicked on
                MessAbstract currentMess = mMessAdapter.getItem(position);
                String messUID= currentMess.getMessUID();
                str=messUID;
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Intent anotherActivityIntent = new Intent(getActivity(), contact.class);
               //anotherActivityIntent.putExtra("MESSNAME",currentMess.getMessName());
                startActivity(anotherActivityIntent);


            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getActivity().setTitle("Home");
    }
    public void loadData()
    {
        mMessListView = (ListView) getView().findViewById(R.id.messageListView);
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");

        // Initialize message ListView and its adapter
        List<MessAbstract> mess = new ArrayList<>();
        mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
        mMessListView.setAdapter(mMessAdapter);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessAdapter.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                    mMessAdapter.add(messAbstract);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void loadData(int type,String area, int service)
    {
        mMessListView = (ListView) getView().findViewById(R.id.messageListView);
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");

        // Initialize message ListView and its adapter
        List<MessAbstract> mess = new ArrayList<>();
        mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
        mMessListView.setAdapter(mMessAdapter);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessAdapter.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                    mMessAdapter.add(messAbstract);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void loadData(int n)
    {
        mMessListView = (ListView) getView().findViewById(R.id.messageListView);
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");

        // Initialize message ListView and its adapter
        List<MessAbstract> mess = new ArrayList<>();
        mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
        mMessListView.setAdapter(mMessAdapter);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessAdapter.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                    mMessAdapter.add(messAbstract);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort By ...");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:{
                        mMessAdapter.sort(0);
                        Toast.makeText(getContext(), "Sorted by Rate", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 1: {
                        Toast.makeText(getContext(), "Sorted by Ratings", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 2: {
                        mMessAdapter.sort(2);
                        Toast.makeText(getContext(), "Sorted by Name", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

}
