package com.davkovania.system.silvia.systemdavkovania.Windows;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.Medicine;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Entities.AESCrypt;
import com.davkovania.system.silvia.systemdavkovania.Entities.Defaults;
import com.davkovania.system.silvia.systemdavkovania.Entities.UserUtil;
import com.davkovania.system.silvia.systemdavkovania.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView signUpLink;
    private Button login;
    private ArrayList<CurrentMedicine> tmpList;
    private ArrayList<User> currUser;
    SharedPreferences prefs;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        signUpLink = findViewById(R.id.link_signup);
        login = findViewById(R.id.btn_login);


        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.API_KEY);

         prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);

        if (UserUtil.isLoogedIn(prefs)) {
            //todo dotiahn8t ho znova ak by nahodou sa zmenilo v db
            Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainPage);
            //currUser.contains(getSharedPreferences("USER_PREFS", MODE_PRIVATE));
            //getCurrentMedicines();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prihlas();
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });


    }

    public void getCurrentMedicines (){


        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause( "userID = '"+ getSharedPreferences(UserUtil.PREFS_NAME, UserUtil.PREFS_MODE)
                .getString("id",null)+ "'" );

        Backendless.Data.of(CurrentMedicine.class).find(dataQuery, new AsyncCallback<List<CurrentMedicine>>(){

            @Override
            public void handleResponse(List<CurrentMedicine> response) {

                tmpList = new ArrayList<>(response);
                getMedicines();
            }


            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public void getMedicines(){

        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setPageSize(100);


        Medicine.findAsync(dataQuery, new AsyncCallback<List<Medicine>>() {
            @Override
            public void handleResponse(List<Medicine> response) {

                ArrayList<Medicine> medicines = new ArrayList<>(response);
                Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
                mainPage.putExtra("currentMedicines", tmpList);
                mainPage.putExtra("allMedicines", medicines);
                mainPage.putExtra("currentUser", currUser);
                startActivity(mainPage);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }


    public void prihlas (){

        if(!(username.getText()!= null && !username.getText().toString().isEmpty()
                && password.getText()!=null && !password.getText().toString().isEmpty())) {
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
                           // currUser = new ArrayList<>(response);
                            UserUtil.saveUserToSharedPteferencie(user, prefs);
                            Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainPage);
                            //setPreferencies(user);
                            //getCurrentMedicines();

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



//    private void setPreferencies (User user){
//        SharedPreferences.Editor editor = getSharedPreferences("USER_PREFS", MODE_PRIVATE).edit();
//        editor.putString("username", user.getUsername());
//        editor.putString("name", user.getName());
//        editor.putString("surname", user.getSurname());
//        editor.putString("emial", user.getEmail());
//        editor.putString("id", user.getObjectId());
//        editor.putBoolean("isLogged", true);
//        editor.apply();
//    }

}

