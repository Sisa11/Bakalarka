package com.davkovania.system.silvia.systemdavkovania.Entities;

import android.app.Application;

public class DataApplication extends Application
{
    private String chosenTable;

    public String getChosenTable()
    {
        return chosenTable;
    }

    public void setChosenTable( String chosenTable )
    {
        this.chosenTable = chosenTable;
    }
}

