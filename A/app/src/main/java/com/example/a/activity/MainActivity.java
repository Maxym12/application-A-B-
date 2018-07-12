package com.example.a.activity;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a.R;
import com.example.a.adapter.ViewPagerAdapter;
import com.example.a.fragment.HistoryFragment;
import com.example.a.fragment.TestFragment;
import com.example.a.room.AppDatabase;
import com.example.a.room.LinkDao;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static LinkDao linkDao;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean sortByStatus = true;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.myToolbar));

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TestFragment(), "Тест");
        viewPagerAdapter.addFragment(new HistoryFragment(), "История");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "roomDatabase")
                .allowMainThreadQueries()
                .build();
        linkDao = db.linkDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (viewPager.getCurrentItem() == 1) {
            if (sortByStatus) {
                // sort by status
                Collections.sort(HistoryFragment.getAll(), (link, link2) -> link.status - link2.status);
                sortByStatus = false;
                showToast("Отсортировано по статусу");
                HistoryFragment.getMa().notifyDataSetChanged();
            } else {
                // sort by data
                Collections.sort(HistoryFragment.getAll(), (link, link2) -> compareDate(link.date, link2.date));
                sortByStatus = true;
                showToast("Отсортировано по дате");
                HistoryFragment.getMa().notifyDataSetChanged();
            }
        } else {
            showToast("Перейдите в Историю");
        }

        return super.onOptionsItemSelected(item);
    }

    private int compareDate(String str, String str2) {
        String[] array = str.split(":");
        String[] array2 = str2.split(":");
        Integer[] array3 = new Integer[6];
        Integer[] array4 = new Integer[6];
        for (int i = 0; i < 6; i++) {
            array3[i] = Integer.valueOf(array[i]);
            array4[i] = Integer.valueOf(array2[i]);
        }
        for (int i = 0; i < 6; i++) {
            if (array3[i].compareTo(array4[i]) != 0) {
                return array4[i].compareTo(array3[i]);
            }
        }
        return 0;
    }

    public void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

    public static LinkDao getLinkDao() {
        return linkDao;
    }
}
