package com.davkovania.system.silvia.systemdavkovania.Entities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.io.Serializable;

public class Item extends AppCompatActivity implements Serializable {

    private int mImageView;
    private String textV1 ;
    private String textV2;
    private boolean switchView;


    public Item(int mImageView1, String text1, String text2, boolean switchView1) {
        mImageView = mImageView1;
        textV1 = text1;
        textV2 = text2;
        switchView = switchView1;
    }

    public int getmImageView() {
        return mImageView;
    }

    public String getTextV1() {
        return textV1;
    }

    public String getTextV2() {
        return textV2;
    }

    public boolean isSwitchView() {
        return switchView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }
}
