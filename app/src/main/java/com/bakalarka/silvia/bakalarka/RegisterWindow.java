package com.bakalarka.silvia.bakalarka;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bakalarka.silvia.bakalarka.entities.User;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RegisterWindow extends AppCompatActivity {

    private Button registruj;
    private EditText name;
    private EditText surname;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_window);

        registruj = findViewById(R.id.id_registruj);
         name = findViewById(R.id.id_name_input);
         surname = findViewById(R.id.id_surname_input);
        username = findViewById(R.id.id_username_input);
       password = findViewById(R.id.id_pass);


        registruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            registracia();
            }
        });
    }

    class CreateuserRecordTask extends AsyncTask<Void, Void, User>
    {
        User user = new User();

        @Override
        protected void onPreExecute()
        {

           user.setSurname( surname.getText().toString() );
           user.setName( name.getText().toString() );
            try {
                user.setPassword( AESCrypt.encrypt(password.getText().toString()) );
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setUsername( username.getText().toString() );
        }

        @Override
        protected User doInBackground( Void... voids )
        {
            return user.save();
        }
    };


    public void registracia (){



            try
            {
                User user = new CreateuserRecordTask().execute().get( 30, TimeUnit.SECONDS );
            }
            catch ( Exception e )
            {
                Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                return;
            }

            Intent nextIntent = new Intent( RegisterWindow.this, WelkomWindow.class );
            startActivity( nextIntent );
        }
    }

