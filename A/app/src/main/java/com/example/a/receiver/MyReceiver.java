package com.example.a.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.a.fragment.HistoryFragment;
import com.example.a.activity.MainActivity;
import com.example.a.room.Link;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String imageURL = intent.getStringExtra("IMAGE_URL");
        int imageStatus = intent.getIntExtra("IMAGE_STATUS", 3);
        String imageDate = intent.getStringExtra("IMAGE_DATE");
        int imageID = MainActivity.linkDao.getAll().size();

        Link link = new Link(imageID, imageURL, imageStatus, imageDate);

        MainActivity.linkDao.insert(link);
        HistoryFragment.getAll().add(link);
        HistoryFragment.getMa().notifyDataSetChanged();
    }
}
