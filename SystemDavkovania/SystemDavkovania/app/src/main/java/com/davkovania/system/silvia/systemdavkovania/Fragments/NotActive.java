package com.davkovania.system.silvia.systemdavkovania.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.Medicine;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Entities.Item;
import com.davkovania.system.silvia.systemdavkovania.Entities.ItemClickListener;
import com.davkovania.system.silvia.systemdavkovania.Entities.RecyclerViewAdapter;
import com.davkovania.system.silvia.systemdavkovania.Entities.UserUtil;
import com.davkovania.system.silvia.systemdavkovania.R;
import com.davkovania.system.silvia.systemdavkovania.Windows.DetailActivity;

import java.util.ArrayList;


public class NotActive extends Fragment {


    RecyclerView recycView;
    RecyclerView.Adapter recAdap;
    RecyclerView.LayoutManager recLayoutManager;
    ArrayList<Medicine> notActiveMedicines = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_not_active, container, false);

       // activeMedicines = (ArrayList<Medicine>) getArguments().getSerializable("activeMedicines");
        User user = UserUtil.getUserFromSharedPreferencies(this.getActivity().getSharedPreferences(UserUtil.PREFS_NAME, UserUtil.PREFS_MODE));
        //activeMedicines = (ArrayList<Medicine>) getArguments().getSerializable("activeMedicines");
        if(user.getCurrentMedicines()!= null) {
            for (CurrentMedicine cm : user.getCurrentMedicines()) {
                if (!cm.getActive()) {
                    notActiveMedicines.add(cm.getMedicine());
                }
            }
        }

        recycView = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recycView.setHasFixedSize(true);
        recLayoutManager = new LinearLayoutManager(getContext());
        recAdap = new RecyclerViewAdapter(itemList, getContext(),
                new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getContext(), DetailActivity.class);
                        intent.putExtra("name", itemList.get(position).getTextV1());
                        startActivity(intent);
                    }
                });


        recycView.setLayoutManager(recLayoutManager);
        recycView.setAdapter(recAdap);


        for (Medicine s : notActiveMedicines) {
            itemList.add(new Item(R.drawable.ic_access_alarm, s.getName(), "skuska", false));
        }

        return view;

    }

}
