package com.davkovania.system.silvia.systemdavkovania.Windows;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.davkovania.system.silvia.systemdavkovania.R;

import static android.os.PowerManager.FULL_WAKE_LOCK;

public class AlarmActivity extends AppCompatActivity {

    private Ringtone ringtone;

    private Button disableButton;
    private PowerManager.WakeLock wake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        disableButton = findViewById(R.id.disableButton);
        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableAlarm();
            }
        });

        turnOnScreen();

        playAlarm();
    }

    private void turnOnScreen() {
        KeyguardManager.KeyguardLock lock = ((KeyguardManager) getSystemService(KEYGUARD_SERVICE)).newKeyguardLock(
                KEYGUARD_SERVICE);
        PowerManager powerManager = ((PowerManager) getSystemService(Context.POWER_SERVICE));
        wake = powerManager.newWakeLock(FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myapp:TAG");

        lock.disableKeyguard();
        wake.acquire();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        );
    }

    private void playAlarm(){
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        if(alert == null){
            // alert is null, using backup
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            // I can't see this ever being null (as always have a default notification)
            // but just incase
            if(alert == null) {
                // alert backup is null, using 2nd backup
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }

        if(alert != null) {
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), alert);
            ringtone.play();
        }
    }

    private void disableAlarm() {
        if(ringtone != null) {
            ringtone.stop();
        }
        if(wake != null){
            wake.release();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        disableAlarm();
    }
}
