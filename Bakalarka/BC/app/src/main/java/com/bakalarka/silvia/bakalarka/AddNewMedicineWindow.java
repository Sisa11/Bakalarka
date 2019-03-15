package com.bakalarka.silvia.bakalarka;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddNewMedicineWindow extends AppCompatActivity {
    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_medicine_window);

        Button save = findViewById(R.id.id_uloz);
        notificationHelper = new NotificationHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sendOnChannel("nazov", "info");
            }
        });


    }

    public void sendOnChannel(String title, String message){
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title, message);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
