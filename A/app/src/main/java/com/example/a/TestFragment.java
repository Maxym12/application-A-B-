package com.example.a;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestFragment extends Fragment {
    private Button btnOk;
    private EditText link;

    public TestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.test_fragment, container, false);

        link = rootView.findViewById(R.id.link);

        btnOk = rootView.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(e -> {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);

            Intent i = new Intent("com.example.b.MainActivity");
            i.putExtra("FROM", "OK");
            i.putExtra("IMAGE_ID", MainActivity.linkDao.getAll().size());
            i.putExtra("IMAGE_LINK", link.getText().toString());
            try {
                startActivity(i);
            } catch (ActivityNotFoundException ex) {
                showToast("Приложение B не установлено");
            }
        });


        return rootView;
    }

    void showToast(String str) {
        Toast.makeText(this.getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
