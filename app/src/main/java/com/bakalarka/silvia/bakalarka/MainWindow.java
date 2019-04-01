package com.bakalarka.silvia.bakalarka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.backendless.Backendless;
import com.bakalarka.silvia.bakalarka.entities.User;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        FloatingActionButton ad_button = findViewById(R.id.id_plus);
        Button logout = findViewById(R.id.id_logout);
        final TextView name = findViewById(R.id.id_pouz_meno);

        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        name.setText(prefs.getString("name", ""));

        ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addingPage = new Intent(MainWindow.this, AddNewMedicineWindow.class);
                startActivity(addingPage);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logingOut();

            }
        });

    }

    public void logingOut(){
        SharedPreferences.Editor editor = getSharedPreferences("USER_PREFS", MODE_PRIVATE).edit();
        editor.remove("username");
        editor.remove("name");
        editor.remove("surname");
        editor.remove("id");
        editor.putBoolean("isLogged", false);
        editor.apply();
        Intent loginPage = new Intent(MainWindow.this, WelkomWindow.class);
        loginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginPage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logingOut();
    }
}
