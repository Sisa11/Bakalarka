package com.davkovania.system.silvia.systemdavkovania.Windows;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.davkovania.system.silvia.systemdavkovania.Entities.AlarmTriggerBroadcastReceiver;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlarmManagerActivity extends AppCompatActivity {

    public static final String ALARM_DESCRIPTION = "ALARM_DESCRIPTION";

    private AlarmManager alarmManager = null;

    private Map<String, PendingIntent> pendingIntents = new HashMap<>();

    private Button setAlarmButton;
    private Button removeAlarmButton;
    ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        setAlarmButton = findViewById(R.id.setAlarmButton);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        removeAlarmButton = findViewById(R.id.removeAlarmButton);
        removeAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlarm();
            }
        });
    }

    private void setAlarm() {
//        Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);

        //TODO pre kazdy alarm treba vytvorit unikatne id
        //pendingIntents.put("FIRST_ALARM", pendingIntent);

        long alarmStartTime = System.currentTimeMillis();
        long alarmExecuteInterval = 60*1000;

//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);

//        Toast.makeText(getApplicationContext(),
//                "Za minutu budem zvonit",
//                Toast.LENGTH_LONG
//        ).show();

        for(int i = 0; i < intentArray.size(); ++i)
        {
            Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);

            intentArray.add(pendingIntent);
            Toast.makeText(getApplicationContext(),
                    "Za minutu budem zvonit",
                    Toast.LENGTH_LONG
            ).show();
        }

    }

    private void removeAlarm(){


            if(intentArray.size()>0){
                for(int i=0; i<intentArray.size(); i++){
                    alarmManager.cancel(intentArray.get(i));
                }
                intentArray.clear();
            }

//        if(pendingIntents.containsKey("FIRST_ALARM")) {
//            alarmManager.cancel(pendingIntents.remove("FIRST_ALARM"));
//
//            Toast.makeText(getApplicationContext(),
//                    "Alarm vymazany",
//                    Toast.LENGTH_LONG
//            ).show();
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "Ziadny alarm nie je nastaveny",
//                    Toast.LENGTH_LONG
//            ).show();
//        }
    }
}
