package com.davkovania.system.silvia.systemdavkovania.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.davkovania.system.silvia.systemdavkovania.Entities.Item;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.util.ArrayList;


public class Info extends Fragment {

    private String nameOfMedic;
    private TextView nameMed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info, container, false);

        nameOfMedic = getArguments().getString("name");
        nameMed = (TextView) view.findViewById(R.id.usernameV);

        nameMed.setText(nameOfMedic);

        return view;
    }
}
