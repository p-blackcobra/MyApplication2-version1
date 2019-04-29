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

        MessAbstract message = getItem(position);
            messName.setText(message.getMessName());
             messRate.setText(message.getMessRate());
             messType.setText(message.getMessType());


             messName.setVisibility(View.VISIBLE);
        messRate.setVisibility(View.VISIBLE);
        messType.setVisibility(View.VISIBLE);

        return convertView;
    }
}
