package com.davkovania.system.silvia.systemdavkovania.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.davkovania.system.silvia.systemdavkovania.Database.Medicine;
import com.davkovania.system.silvia.systemdavkovania.Entities.Item;
import com.davkovania.system.silvia.systemdavkovania.Entities.RecyclerViewAdapter;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.util.ArrayList;


public class NotActive extends Fragment {


    RecyclerView recycView;
    RecyclerView.Adapter recAdap;
    RecyclerView.LayoutManager recLayoutManager;
    ArrayList<Medicine> activeMedicines = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_not_active, container, false);

        activeMedicines = (ArrayList<Medicine>) getArguments().getSerializable("activeMedicines");

        recycView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recycView.setHasFixedSize(true);
        recLayoutManager = new LinearLayoutManager(getContext());
        recAdap = new RecyclerViewAdapter(itemList, getContext());

        recycView.setLayoutManager(recLayoutManager);
        recycView.setAdapter(recAdap);


        for (Medicine s : activeMedicines) {
            itemList.add(new Item(R.drawable.ic_access_alarm, s.getName(), "skuska", false));
        }

        return view;

    }

}
