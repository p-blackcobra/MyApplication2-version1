package com.example.nk.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessAdapter extends ArrayAdapter<MessAbstract> {
    public MessAdapter(@NonNull Context context, int resource, @NonNull List<MessAbstract> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_mess, parent, false);
        }
        TextView messName = (TextView) convertView.findViewById(R.id.mess_name);
        TextView messRate = (TextView) convertView.findViewById(R.id.rate);
        TextView messType = (TextView) convertView.findViewById(R.id.type);
        ImageView messImageView = (ImageView)convertView.findViewById(R.id.mess_tiffin);

        MessAbstract message = getItem(position);
            messName.setText(message.getMessName());
             messRate.setText(message.getMessRate());
             messType.setText(message.getMessType());
        Glide.with(messImageView.getContext())
                .load(message.getPhotoURL())
                .into(messImageView);

             messName.setVisibility(View.VISIBLE);
        messRate.setVisibility(View.VISIBLE);
        messType.setVisibility(View.VISIBLE);
messImageView.setVisibility(View.VISIBLE);
        return convertView;
    }

    public void sort(int i) {

        if(i==0)
        {
            super.sort(new RateComparator());
        }
        else if(i==1)
        {

        }
        else
        {
            super.sort(new NameComparator());
        }


        notifyDataSetChanged();
    }
}
