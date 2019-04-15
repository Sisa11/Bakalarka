package com.davkovania.system.silvia.systemdavkovania.Windows;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Entities.AESCrypt;
import com.davkovania.system.silvia.systemdavkovania.R;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button signUp;
    private TextView logInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.input_name);
        surname = findViewById(R.id.input_surname);
        email = findViewById(R.id.input_email);
        username = findViewById(R.id.input_login);
        password = findViewById(R.id.input_pass);
        confirmPassword = findViewById(R.id.input_confirm_password);
        signUp = findViewById(R.id.btn_signup);
        logInLink = findViewById(R.id.link_login);

        logInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    confirmPassword.setError(null);
                    registracia();
                } else {
                    confirmPassword.setError("Hesla sa nezhoduju!");
                }

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        if (name.getText().toString().isEmpty() || name.length() < 3) {
            name.setError("Meno musi obsahovat aspon 3 pismena!");
            valid = false;
        } else {
            name.setError(null);
        }

        if (surname.getText().toString().isEmpty() || surname.length() < 3) {
            surname.setError("Priezvisko musi obsahovat aspon 3 pismena!");
            valid = false;
        } else {
            surname.setError(null);
        }

        if (email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError("Nespravny format e-mailovej adresy!");
            valid = false;
        } else {
            email.setError(null);
        }

        if (password.getText().toString().isEmpty() || password.length() < 8) {
            password.setError("Heslo musi obsahovat aspon 8 znakov!");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


    class CreateuserRecordTask extends AsyncTask<Void, Void, User>
    {
        User user = new User();

        @Override
        protected void onPreExecute()
        {
            user.setSurname( surname.getText().toString() );
            user.setName( name.getText().toString() );
            user.setUsername( username.getText().toString() );
            user.setEmail( email.getText().toString() );
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

        Intent nextIntent = new Intent( SignUpActivity.this, LoginActivity.class );
        startActivity( nextIntent );
    }
}

