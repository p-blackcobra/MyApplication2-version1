package com.example.nk.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.nk.myapplication.R.color.white;

public class showing_list extends Fragment{

    CharSequence[] values = { " Mess Rates "," Rating "," Mess Name "};
public static  String str1;
AlertDialog alertDialog1;
    private Button btn_filter;
    private  Button btn_sortby;
    public static int veg, service_type;
    public static String area;
    String keywrd;
    int color;

    String area_name;
    private static final String TAG = "Show_list";
    private  TextView noresult;

    private ListView mMessListView;
    private MessAdapter mMessAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        color=getResources().getColor(white);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        area_name = Home.selected_location;
        btn_filter = (Button)getView().findViewById(R.id.filters);
        btn_sortby = (Button)getView().findViewById(R.id.sortby);
        noresult=getView().findViewById(R.id.noresult);
        swipeRefreshLayout =(SwipeRefreshLayout)getView().findViewById(R.id.pulltR);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadData();


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
                        Spinner spinner_service =(Spinner)dialog.findViewById(R.id.spinner_service);

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
                String messUID = currentMess.getMessUID();
                str1=messUID;
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Intent anotherActivityIntent = new Intent(getActivity(), contact.class);
                //anotherActivityIntent.putExtra("MESSNAME",currentMess.getMessName());
                startActivity(anotherActivityIntent);


            }
        });

        getActivity().setTitle(area_name);

    }

    public void loadData()
    {
        mMessListView = (ListView) getView().findViewById(R.id.mess_list);
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("messdet");

        // Initialize message ListView and its adapter
        List<MessAbstract> mess = new ArrayList<>();
        mMessAdapter = new MessAdapter(getActivity(), R.layout.item_mess,mess);
        mMessListView.setAdapter(mMessAdapter);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessAdapter.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    MessAbstract messAbstract = ds.getValue(MessAbstract.class);
                    if(area_name.equalsIgnoreCase("all"))
                    {
                        mMessAdapter.add(messAbstract);
                    }
                   else
                    {
                        if(messAbstract.getArea().toString().equalsIgnoreCase(area_name))
                        {
                            mMessAdapter.add(messAbstract);
                        }
                    }

                }

                if(mMessAdapter.isEmpty())
                {
                    noresult.setVisibility(View.VISIBLE);
                    //getView().setBackgroundColor(white);
                }
                else
                {
                    noresult.setVisibility(View.GONE);
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
        builder.setIcon(R.drawable.ic_sort_black_24dp);
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
