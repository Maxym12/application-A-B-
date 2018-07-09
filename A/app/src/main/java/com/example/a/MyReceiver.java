package com.example.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.example.a.room.Link;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String imageURL = intent.getStringExtra("IMAGE_URL");
        int imageStatus = intent.getIntExtra("IMAGE_STATUS", 3);
        int imageID = intent.getIntExtra("IMAGE_ID", -1);

        Link link = new Link(imageID, imageURL, imageStatus);

        MainActivity.linkDao.insert(link);
        HistoryFragment.getAll().add(link);
        HistoryFragment.getMa().notifyDataSetChanged();
    }
}
