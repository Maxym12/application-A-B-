package com.example.a;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a.room.Link;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private static ArrayList<Link> all;
    private static CustomLinkAdapter ma;
    private static ListView lv;

    public HistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history_fragment, container, false);

        all = new ArrayList<>();
        lv = rootView.findViewById(R.id.listView);
        ma = new CustomLinkAdapter(getActivity(), R.layout.adapter_view_layout, all);
        lv.setAdapter(ma);

        syncDatabaseWithListView();

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent("com.example.b.MainActivity");
            intent.putExtra("FROM", "HISTORY");
            intent.putExtra("IMAGE_LINK", all.get(i).imageLink);
            intent.putExtra("IMAGE_STATUS", all.get(i).status);
            startActivity(intent);
        });

        return rootView;
    }

    private void syncDatabaseWithListView() {
        for(Link temp : MainActivity.linkDao.getAll()) {
            all.add(temp);
        }
        ma.notifyDataSetChanged();
    }

    public static ArrayList<Link> getAll() {
        return all;
    }

    public static CustomLinkAdapter getMa() {
        return ma;
    }

}
