package com.example.nk.myapplication.Model;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.example.nk.myapplication.Model.Location;

import java.util.ArrayList;

public class Retrieve_loc_from_firebase {

    DatabaseReference db;
    Boolean saved=null;

    public Retrieve_loc_from_firebase(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public  Boolean save(Location location)
    {
        if(location==null)
        {
            saved=false;
        }
        else
        {
            try
            {
                db.child("Location").push().setValue(location);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }
    //READ
    public ArrayList<String> retrieve()
    {
        final ArrayList<String> locations=new ArrayList<>();
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,locations);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,locations);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return locations;
    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> locations)
    {
        locations.clear();
        for (DataSnapshot ds:snapshot.getChildren())
        {
            String name=ds.getValue(Location.class).getName();
            locations.add(name);
        }

    }
}
