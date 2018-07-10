package com.example.a.fragment;

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

import com.example.a.R;

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
            Intent i = new Intent("com.example.b.MainActivity");
            i.putExtra("FROM", "OK");
            i.putExtra("IMAGE_LINK", link.getText().toString());
            startActivity(i);
        });

        return rootView;
    }
}
