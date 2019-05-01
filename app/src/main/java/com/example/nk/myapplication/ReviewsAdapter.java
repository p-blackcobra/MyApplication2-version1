package com.example.nk.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewsAdapter extends ArrayAdapter<Reviews> {
public ReviewsAdapter(@NonNull Context context, int resource, @NonNull List<Reviews> objects) {
        super(context, resource, objects);
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_reviews, parent, false);
        }
        TextView messName = (TextView) convertView.findViewById(R.id.username);
        TextView rating = (TextView) convertView.findViewById(R.id.ratings);
        TextView comment = (TextView) convertView.findViewById(R.id.comment);

        Reviews message = getItem(position);
        messName.setText(message.getUserName());
        rating.setText(message.getRatingCount().toString());
        comment.setText(message.getComment());


        messName.setVisibility(View.VISIBLE);
        rating.setVisibility(View.VISIBLE);
        comment.setVisibility(View.VISIBLE);

        return convertView;
        }
        }

