package com.davkovania.system.silvia.systemdavkovania.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.davkovania.system.silvia.systemdavkovania.Entities.Item;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.util.ArrayList;


public class Notifications extends Fragment {

    //ArrayList<Item> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notifications, container, false);

       // itemList = (ArrayList<Item>) getArguments().getSerializable("currentItem");

        return view;
    }
}

