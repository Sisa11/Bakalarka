package com.davkovania.system.silvia.systemdavkovania.Entities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.davkovania.system.silvia.systemdavkovania.Windows.AlarmActivity;

public class AlarmTriggerBroadcastReceiver extends BroadcastReceiver {

    private final static String TAG_ALARM_TRIGGER_BROADCAST = "ALARM_TRIGGER_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG_ALARM_TRIGGER_BROADCAST, "Broadcast received");

        startAlarmActivity(context);
    }

    private void startAlarmActivity(Context context){
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
