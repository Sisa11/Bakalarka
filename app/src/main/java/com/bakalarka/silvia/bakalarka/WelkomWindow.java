package com.bakalarka.silvia.bakalarka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.QueryOptions;
import com.bakalarka.silvia.bakalarka.entities.User;

import java.util.Iterator;
import java.util.List;

public class WelkomWindow extends AppCompatActivity {

    private TextView username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( this, Defaults.APPLICATION_ID, Defaults.API_KEY );

        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);

        if(prefs.getBoolean("isLogged", false)){
            Intent mainPage = new Intent(WelkomWindow.this, MainWindow.class);
            startActivity(mainPage);
        }

        Button prihlas_button = findViewById(R.id.id_button_prihlas);
        Button registruj_button = findViewById(R.id.id_registracia_button);
       username = findViewById(R.id.id_login_input);
       password = findViewById(R.id.id_password_input);

        prihlas_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prihlas();
            }
        });




        registruj_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelkomWindow.this, RegisterWindow.class);
                startActivity(intent);
            }
        });
    }

    public void prihlas (){

        if(!(username.getText()!= null && !username.getText().toString().isEmpty() && password.getText()!=null &&!password.getText().toString().isEmpty())) {
            return ;
        }

        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause( "username = '"+ username.getText()+ "'" );

       Backendless.Data.of(User.class).find(dataQuery, new AsyncCallback<List<User>>(){

           @Override
           public void handleResponse(List<User> response) {

            if(response != null && !response.isEmpty()){
                User user = response.get(0);
                try {
                    if(user.getPassword().equals(AESCrypt.encrypt(password.getText().toString()))){
                        setPreferencies(user);
                        Intent mainPage = new Intent(WelkomWindow.this, MainWindow.class);
                        startActivity(mainPage);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
           }

           @Override
           public void handleFault(BackendlessFault fault) {

           }
       });
    }

    private void setPreferencies (User user){
        SharedPreferences.Editor editor = getSharedPreferences("USER_PREFS", MODE_PRIVATE).edit();
        editor.putString("username", user.getUsername());
        editor.putString("name", user.getName());
        editor.putString("surname", user.getSurname());
        editor.putString("id", user.getObjectId());
        editor.putBoolean("isLogged", true);
        editor.apply();
    }
}
