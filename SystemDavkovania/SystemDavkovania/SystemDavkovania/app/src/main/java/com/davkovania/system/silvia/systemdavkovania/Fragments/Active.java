package com.davkovania.system.silvia.systemdavkovania.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.util.ArrayList;


public class Active extends Fragment {

    RecyclerView name;
    ArrayList<CurrentMedicine> activeMedicines = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_active, container, false);
//        name = (TextView)view.findViewById(R.id.recycleView);
//        activeMedicines = (ArrayList<CurrentMedicine>) getArguments().getSerializable("activeMedicine");
//        String m = "";
//        for(CurrentMedicine s: activeMedicines){
//            m+= s.getMedicineID();
//            m += " \n";
//
//        }
//        name.setText(m);
        return view;

    }
}
