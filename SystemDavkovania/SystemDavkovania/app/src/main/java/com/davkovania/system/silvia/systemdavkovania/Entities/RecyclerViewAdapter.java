package com.davkovania.system.silvia.systemdavkovania.Entities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.davkovania.system.silvia.systemdavkovania.R;
import com.davkovania.system.silvia.systemdavkovania.Windows.DetailActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> {
    //private ArrayList<Item> listData = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<Item> listData, Context context, ItemClickListener itemClickListener){
        this.mitemList = listData;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }
    private ItemClickListener itemClickListener;
    private ArrayList<Item> mitemList;

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        public ImageView imageV;
        public TextView mtextView1;
        public TextView mtextView2;
        public Switch switchView;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            imageV = (ImageView) itemView.findViewById(R.id.imageView);
            mtextView1 = (TextView) itemView.findViewById(R.id.textView);
            mtextView2 = (TextView) itemView.findViewById(R.id.textView2);
            switchView = (Switch) itemView.findViewById(R.id.simpleSwitch);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

        public void setItemClickListener(ItemClickListener clickListener) {
            itemClickListener = clickListener;
        }
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        RecycleViewHolder rvh = new RecycleViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        Item currentItem = mitemList.get(position);
        holder.imageV.setImageResource(currentItem.getmImageView());
        holder.mtextView1.setText(currentItem.getTextV1());
        holder.mtextView2.setText(currentItem.getTextV2());
        holder.switchView.setChecked(currentItem.isSwitchView());
        holder.setItemClickListener(itemClickListener);
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if(isLongClick){
//                    Toast.makeText(context, "Lond Click: "+ mitemList.get(position), Toast.LENGTH_SHORT);
//                }
//                    else{
//                    Log.d(TAG, "onClick:" + mitemList.get(position));
//                    //Toast.makeText(context, " "+ mitemList.get(position), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra("name", mitemList.get(position).getTextV1());
//                    context.startActivity(intent);
//
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mitemList.size();
    }
}
