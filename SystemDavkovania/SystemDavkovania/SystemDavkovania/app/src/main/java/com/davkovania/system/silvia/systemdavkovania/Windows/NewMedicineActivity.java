package com.davkovania.system.silvia.systemdavkovania.Windows;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.Medicine;
import com.davkovania.system.silvia.systemdavkovania.R;


import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewMedicineActivity extends AppCompatActivity {

    private Button cancel;
    private Button save;
    private EditText numberOfPills;
    private EditText numberOfPackage;
    private EditText date;
    private FloatingActionButton clock;
    AutoCompleteTextView nameOfMedicine;

    ArrayAdapter<String> adapter;

    Map<String, String> medicinesMap = new HashMap<>();
    private Date newDate;
    private Date newTime;
    Date datetime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        cancel = findViewById(R.id.btn_cancel);
        save = findViewById(R.id.btn_save);
        numberOfPills = findViewById(R.id.input_pills);
        numberOfPackage = findViewById(R.id.input_balenie);
        nameOfMedicine = findViewById(R.id.input_nazov);
        date = findViewById(R.id.input_date);
        clock = findViewById(R.id.alarm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(NewMedicineActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendOnChannel("nazov", "info");
                uloz();
                Intent intentDetail = new Intent(NewMedicineActivity.this, DetailActivity.class);
                startActivity(intentDetail);
            }
        });
        getMedicines();

        if(getActionBar()!= null){
            getActionBar().hide();
        }

        if(getSupportActionBar()!= null){
            getSupportActionBar().hide();
        }

    }


    public void getMedicines(){

        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setPageSize(100);


        Medicine.findAsync(dataQuery, new AsyncCallback<List<Medicine>>() {
            @Override
            public void handleResponse(List<Medicine> response) {

                List<String> s = new ArrayList<>();
                for(Medicine m: response){
                    s.add(m.getName());
                    medicinesMap.put(m.getName(), m.getObjectId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewMedicineActivity.this,
                        android.R.layout.simple_dropdown_item_1line, s);


                nameOfMedicine.setAdapter(adapter);


            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }


    class CreateNewRecord extends AsyncTask<Void, Void, CurrentMedicine>
    {
        CurrentMedicine rekord = new CurrentMedicine();

        @Override
        protected void onPreExecute()
        {

            datetime = new Date(
                    newDate.getYear(),
                    newDate.getMonth(),
                    newDate.getDate(),
                    newTime.getHours(),
                    newTime.getMinutes());

            rekord.setDateOfStart(datetime);
            rekord.setActive(true);
            rekord.setAddingPills(Integer.parseInt(numberOfPills.getText().toString()));
            rekord.setMedicineID(medicinesMap.get(nameOfMedicine.getText().toString()));
            rekord.setUserID(getSharedPreferences("USER_PREFS", MODE_PRIVATE).getString("id", null));
            rekord.setNumberOfPackages(Integer.parseInt(numberOfPackage.getText().toString()));

        }

        @Override
        protected CurrentMedicine doInBackground( Void... voids )
        {
            return rekord.save();
        }

        @Override
        protected void onPostExecute(CurrentMedicine currentMedicine) {
            super.onPostExecute(currentMedicine);
            setStartAlarm();
        }
    }


    public void uloz (){
        try
        {
            CurrentMedicine rekord = new CreateNewRecord().execute().get( 30, TimeUnit.SECONDS );
        }
        catch ( Exception e )
        {
            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
            return;
        }

        Intent backIntent = new Intent( NewMedicineActivity.this, MainActivity.class );
        startActivity( backIntent );
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        ((DatePickerFragment) newFragment).setT(new DatePickerFragment.DatePickerCallback() {
            @Override
            public void createDate(int y, int m, int d) {
                newDate = new Date(y - 1900, m, d);
            }
        });
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
        ((TimePickerFragment) newFragment).setT(new TimePickerFragment.TimePickerCallback() {
            @Override
            public void createDate(int h, int m) {
                newTime = new Date(2019 - 1900, 1, 1, h, m);
            }
        });
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        TimePickerCallback t;

        interface TimePickerCallback{
            void createDate(int h, int m);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }



        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            t.createDate(hourOfDay, minute);

        }

        public void setT(TimePickerCallback t){
            this.t = t;
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        DatePickerFragment.DatePickerCallback t;

        interface DatePickerCallback{
            void createDate(int y, int m, int d);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            t.createDate(year, month, day);
        }

        public void setT(DatePickerCallback t) {
            this.t = t;
        }
    }

    private void  setStartAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0 );

        // if(datetime.before(new Date())){
        //   datetime.
        // } TODOOOO
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, datetime.getTime(), pendingIntent);
    }
}

