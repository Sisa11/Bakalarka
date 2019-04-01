package com.bakalarka.silvia.bakalarka;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.bakalarka.silvia.bakalarka.entities.CurrentMedicine;
import com.bakalarka.silvia.bakalarka.entities.Medicine;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class AddNewMedicineWindow extends FragmentActivity {
    private NotificationHelper notificationHelper;
    Button save;
    EditText number;
    AutoCompleteTextView name;
    Button date;
    Date datetime;
    Button time;

    ArrayAdapter<String> adapter;

    Map<String, String> medicinesMap = new HashMap<>();
    private Date newDate;
    private Date newTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_medicine_window);

        save = findViewById(R.id.id_uloz);
        number = findViewById(R.id.id_number);
        name = findViewById(R.id.id_nameOfMedicine);
        date = findViewById(R.id.id_date);
        time = findViewById(R.id.id_time);


        time.setOnClickListener(new View.OnClickListener() {
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

        notificationHelper = new NotificationHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sendOnChannel("nazov", "info");
            uloz();
            }
        });
        getMedicines();


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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewMedicineWindow.this,
                        android.R.layout.simple_dropdown_item_1line, s);


                name.setAdapter(adapter);


            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }

    public void sendOnChannel(String title, String message){
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title, message);
        notificationHelper.getManager().notify(1, nb.build());
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
            rekord.setIntervalOfIngestion(8);
            rekord.setMedicineID("6B6CDCE4-57D8-2C75-FF3C-794FE94C2100");
            rekord.setUserID("D94210C1-BF76-76D1-FF96-F3FEE2673800");
            rekord.setNumberOfPackages(1);

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

        Intent backIntent = new Intent( AddNewMedicineWindow.this, MainWindow.class );
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
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0 );

       // if(datetime.before(new Date())){
         //   datetime.
       // } TODOOOO
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, datetime.getTime(), pendingIntent);
    }
}
