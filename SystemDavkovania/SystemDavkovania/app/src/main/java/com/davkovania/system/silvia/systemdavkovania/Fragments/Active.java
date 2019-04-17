package com.davkovania.system.silvia.systemdavkovania.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.Medicine;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Entities.Item;
import com.davkovania.system.silvia.systemdavkovania.Entities.ItemClickListener;
import com.davkovania.system.silvia.systemdavkovania.Entities.RecyclerViewAdapter;
import com.davkovania.system.silvia.systemdavkovania.Entities.UserUtil;
import com.davkovania.system.silvia.systemdavkovania.R;
import com.davkovania.system.silvia.systemdavkovania.Windows.DetailActivity;
import com.davkovania.system.silvia.systemdavkovania.Windows.LoginActivity;
import com.davkovania.system.silvia.systemdavkovania.Windows.MainActivity;
import com.davkovania.system.silvia.systemdavkovania.Windows.NewMedicineActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Active extends Fragment {

    RecyclerView recycView;
    RecyclerView.Adapter recAdap;
    RecyclerView.LayoutManager recLayoutManager;
    ArrayList<Medicine> activeMedicines = new ArrayList<>();
    ArrayList<Item> itemList = new ArrayList<>();
    FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_active, container, false);

        //clickItem();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(getActivity(), NewMedicineActivity.class);
                startActivity(intentAdd);
            }
        });


        User user = UserUtil.getUserFromSharedPreferencies(this.getActivity().getSharedPreferences(UserUtil.PREFS_NAME, UserUtil.PREFS_MODE));
        //activeMedicines = (ArrayList<Medicine>) getArguments().getSerializable("activeMedicines");
        if(user.getCurrentMedicines()!= null) {
            for (CurrentMedicine cm : user.getCurrentMedicines()) {
                if (cm.getActive()) {
                    activeMedicines.add(cm.getMedicine());
                }
            }
        }

        recycView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recycView.setHasFixedSize(true);
        recLayoutManager = new LinearLayoutManager(getContext());
        recAdap = new RecyclerViewAdapter(itemList, getContext(),
                new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                if(isLongClick){
//                    Toast.makeText(context, "Lond Click: "+ mitemList.get(position), Toast.LENGTH_SHORT);
//                }
//                    else{
//                    Log.d(TAG, "onClick:" + mitemList.get(position));
                    //Toast.makeText(context, " "+ mitemList.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("name", itemList.get(position).getTextV1());
                    startActivity(intent);

//                }
            }
        });


        recycView.setLayoutManager(recLayoutManager);
        recycView.setAdapter(recAdap);

        for (Medicine s : activeMedicines) {
            itemList.add(new Item(R.drawable.ic_access_alarm, s.getName(), "skuska", true));
        }

        return view;

    }

//    public void clickItem(){
//        for(Item i: itemList){
//            Toast.makeText(getContext(), " "+i, Toast.LENGTH_SHORT).show();
//        }
//
//    }


}
