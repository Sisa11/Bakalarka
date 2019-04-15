package com.davkovania.system.silvia.systemdavkovania.Windows;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Entities.AESCrypt;
import com.davkovania.system.silvia.systemdavkovania.R;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity {

    private EditText nam;
    private EditText surnam;
    private EditText usrnam;
    private EditText mail;
    private Button cancel;
    private Button saveChanges;
    private String id;
    private EditText password;
    ArrayList<User> currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usrnam = findViewById(R.id.usernameV);
        nam = findViewById(R.id.nameV);
        surnam = findViewById(R.id.surnameV);
        mail = findViewById(R.id.mailV);
        password = findViewById(R.id.passV);
        cancel = findViewById(R.id.can);
        saveChanges = findViewById(R.id.change);

        currentUser = (ArrayList<User>) getIntent().getSerializableExtra("currentUser");

        for(User u : currentUser) {
            usrnam.setText(u.getUsername());
            nam.setText(u.getName());
            surnam.setText(u.getSurname());
            mail.setText(u.getEmail());
            try {
                password.setText(AESCrypt.decrypt(u.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            id = u.getObjectId();
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPage = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(mainPage);
            }
        });



        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingChanges();
            }
        });

    }

    class ChangeUserInfo extends AsyncTask<Void, Void, User>
    {

        User user = new User();

        @Override
        protected void onPreExecute()
        {
            user.setObjectId(id);
            user.setName(nam.getText().toString());
            user.setSurname(surnam.getText().toString());
            user.setEmail(mail.getText().toString());
            user.setUsername(usrnam.getText().toString());
            try {
                user.setPassword( AESCrypt.encrypt(password.getText().toString()) );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected User doInBackground( Void... voids )
        {

            return user.save();
        }

    }


    public void savingChanges (){

        try
        {
            User usr = new ChangeUserInfo().execute().get( 30, TimeUnit.SECONDS );
        }
        catch ( Exception e )
        {
            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
            return;
        }

        Intent backIntent = new Intent( ProfileActivity.this, LoginActivity.class );
        startActivity( backIntent );
    }

}
