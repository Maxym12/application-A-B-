package com.example.a.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.a.activity.MainActivity;
import com.example.a.fragment.HistoryFragment;
import com.example.a.room.Link;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra("FOR").equals("INSERT")) {
            int imageID = Link.lastID + 1;
            String imageURL = intent.getStringExtra("IMAGE_URL");
            int imageStatus = intent.getIntExtra("IMAGE_STATUS", 3);
            String imageDate = intent.getStringExtra("IMAGE_DATE");

            Link link = new Link(imageID, imageURL, imageStatus, imageDate);

            MainActivity.getLinkDao().insert(link);
            HistoryFragment.getAll().add(0, link);
            HistoryFragment.getMa().notifyDataSetChanged();
        } else if (intent.getStringExtra("FOR").equals("UPDATE")) {
            int imageID = intent.getIntExtra("IMAGE_ID", -1);
            String imageURL = intent.getStringExtra("IMAGE_URL");
            int imageStatus = intent.getIntExtra("IMAGE_STATUS", 3);
            String imageDate = intent.getStringExtra("IMAGE_DATE");

            Link link = new Link(imageID, imageURL, imageStatus, imageDate);

            MainActivity.getLinkDao().update(link);
            updateFromAllById(imageID);
            HistoryFragment.getMa().notifyDataSetChanged();
        } else {
            int imageID = intent.getIntExtra("IMAGE_ID", -1);
            String imageURL = intent.getStringExtra("IMAGE_URL");

            MainActivity.getLinkDao().deleteById(imageID);
            deleteFromAllById(imageID);
            HistoryFragment.getOpenedLinks().remove(imageURL);
            HistoryFragment.getMa().notifyDataSetChanged();
        }
    }

    private void deleteFromAllById(int id) {
        for (Link temp : HistoryFragment.getAll()) {
            if (temp.id == id) {
                HistoryFragment.getAll().remove(temp);
                return;
            }
        }
    }

    private void updateFromAllById(int id) {
        for (Link temp : HistoryFragment.getAll()) {
            if (temp.id == id) {
                temp.status = 1;
                return;
            }
        }
    }
}