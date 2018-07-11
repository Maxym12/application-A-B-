package com.example.a.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.R;
import com.example.a.room.Link;

import java.util.ArrayList;

public class CustomLinkAdapter extends ArrayAdapter<Link> {
    private Context mContext;
    private int mResource;

    public CustomLinkAdapter(Context context, int resource, ArrayList<Link> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String imageLink = getItem(position).imageLink;
        int status = getItem(position).status;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvLink = convertView.findViewById(R.id.textView);

        tvLink.setText(imageLink);

        switch (status) {
            case 1:
                convertView.setBackgroundColor(convertView.getResources().getColor(R.color.green));
//                convertView.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                convertView.setBackgroundColor(convertView.getResources().getColor(R.color.red));
//                convertView.setBackgroundColor(Color.RED);
                break;
            case 3:
                convertView.setBackgroundColor(convertView.getResources().getColor(R.color.gray));
//                convertView.setBackgroundColor(Color.GRAY);
                break;
        }

        return convertView;
    }
}