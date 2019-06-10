package com.example.nk.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentTwo extends Fragment {
    private static final String TAG = "FargmentTwo";
    private ListView mReviewsListview;
    private ReviewsAdapter mReviwsAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessDatabaseReference;
    private Button btn_submit;
    private EditText commentView;
    private RatingBar ratingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_two, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_submit=(Button)getView().findViewById(R.id.buttonSubmit);
        commentView=(EditText)getView().findViewById(R.id.CommentText) ;
        ratingBar=(RatingBar)getView().findViewById(R.id.ratingBarView) ;

        mReviewsListview = (ListView) getView().findViewById(R.id.revewListView);
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessDatabaseReference = mFirebaseDatabase.getReference().child("reviews").child(showing_list.str1);



        // Initialize message ListView and its adapter
        List<Reviews> mess = new ArrayList<>();
        mReviwsAdapter = new ReviewsAdapter(getActivity(), R.layout.item_reviews,mess);
        mReviewsListview.setAdapter(mReviwsAdapter);
        mMessDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReviwsAdapter.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Reviews messAbstract = ds.getValue(Reviews.class);
                    mReviwsAdapter.add(messAbstract);
                    Log.d("TAG", messAbstract.getUserName());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getContext().getSharedPreferences(Login.MyPREFERENCES, getContext().MODE_PRIVATE);
                String ph=sharedpreferences.getString("Phone","");
                if(ph.length()<1) {
                    Toast.makeText(getContext(),"Please Login First...",Toast.LENGTH_SHORT).show();
                }
                String username=sharedpreferences.getString("Name","");

                float rating = ratingBar.getRating();
                String comment =  commentView.getText().toString();
                if(rating == 0.0  )
                    Toast.makeText(getContext(),"Please select stars to review",Toast.LENGTH_SHORT).show();
                if(comment.length()<1)
                    Toast.makeText(getContext(),"Please enter any comment ",Toast.LENGTH_SHORT).show();
                if(rating!=0.0 && comment.length()>0 && ph.length()>0) {
                    Reviews newReview = new Reviews(username, rating, comment);
                    mMessDatabaseReference.child(ph).setValue(newReview);
                }
            }
        });
}
}