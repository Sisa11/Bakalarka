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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.davkovania.system.silvia.systemdavkovania.Database.CurrentMedicine;
import com.davkovania.system.silvia.systemdavkovania.Database.Medicine;
import com.davkovania.system.silvia.systemdavkovania.Database.Times;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Entities.*;
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
    RecyclerView recycView;
    RecyclerView.Adapter recAdap;
    RecyclerView.LayoutManager recLayoutManager;
    ArrayList<Item> itemList = new ArrayList<>();
    ArrayList<Date> times = new ArrayList<>();
    ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

    ArrayAdapter<String> adapter;

    Map<String, Medicine> medicinesMap = new HashMap<>();
    private Date newDate;
    private Date newTime;
    Date datetime;

    private AlarmManager alarmManager = null;

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

        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        getMedicines();

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
//                Intent intentDetail = new Intent(NewMedicineActivity.this, MainActivity.class);
//                startActivity(intentDetail);
            }
        });


        if (getActionBar() != null) {
            getActionBar().hide();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recycView = findViewById(R.id.recyclerView1);
        recycView.setHasFixedSize(true);
        recLayoutManager = new LinearLayoutManager(this);
        recAdap = new RecyclerViewAdapter(itemList, this,
                new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(NewMedicineActivity.this, Notification.class);
                        //intent.putExtra("name", itemList.get(position).getTextV1());
                        startActivity(intent);
                    }
                });


        recycView.setLayoutManager(recLayoutManager);
        recycView.setAdapter(recAdap);
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
                    medicinesMap.put(m.getName(), m);
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


    class CreateNewRecord extends AsyncTask<Void, Void, User>
    {
        CurrentMedicine rekord = new CurrentMedicine();
        User user = UserUtil.getUserFromSharedPreferencies(getSharedPreferences(UserUtil.PREFS_NAME, UserUtil.PREFS_MODE));

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected User doInBackground( Void... voids )
        {

            List<CurrentMedicine> cl = new ArrayList<>();

            datetime = new Date(
                    newDate.getYear(),
                    newDate.getMonth(),
                    newDate.getDate());
//                    newTime.getHours(),
//                    newTime.getMinutes());

            rekord.setDateOfStart(datetime);
            rekord.setActive(true);
            rekord.setAddingPills(Integer.parseInt(numberOfPills.getText().toString()));
            rekord.setMedicineID(medicinesMap.get(nameOfMedicine.getText().toString()).getObjectId());
            rekord.setUserID(user.getObjectId());
            rekord.setNumberOfPackages(Integer.parseInt(numberOfPackage.getText().toString()));
            rekord.setMedicine(medicinesMap.get(nameOfMedicine.getText().toString()));
            rekord = rekord.save();
            List<Medicine> ml = new ArrayList<>();
            ml.add(medicinesMap.get(nameOfMedicine.getText().toString()));
            Backendless.Data.of(CurrentMedicine.class).addRelation(
                    rekord,
                    "medicine" ,
                    ml
            );
            //after calling rekord.save rekord.medicine was null
            rekord.setMedicine(medicinesMap.get(nameOfMedicine.getText().toString()));

            User userTmp = user.save();
            cl.add(rekord);
            Backendless.Data.of(User.class).addRelation(
                   userTmp,
                    "currentMedicines" ,
                    cl
            );

            if (user.getCurrentMedicines() == null) {
                user.setCurrentMedicines(new ArrayList<CurrentMedicine>());
            }
            user.getCurrentMedicines().add(rekord);
            List<Times> lt = new ArrayList<>();
            for(Date d : times){
                Times t = new Times();
                t.setTime(d);
                lt.add(t.save());
            }
            Backendless.Data.of(CurrentMedicine.class).addRelation(
                    rekord,
                    "times" ,
                    lt
            );
            if(rekord.getTimes() == null){
                rekord.setTimes(new ArrayList<Times>());
            }
            rekord.getTimes().addAll(lt);


            UserUtil.saveUserToSharedPteferencie(user, getSharedPreferences(UserUtil.PREFS_NAME, UserUtil.PREFS_MODE));
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            //TODO uncomment this
//            for(Date time : times) {
//                setStartAlarm(datetime, time);
//            }
            setStartAlarm(times);

            //TODO comment this
            //setStartAlarm(datetime, times.get(0));
        }
    }


    public void uloz (){
        try
        {
            User rekord = new CreateNewRecord().execute().get( 30, TimeUnit.SECONDS );
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
                date.setText(newDate.toString());
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
                    times.add(newTime);
                    itemList.add(new Item(R.drawable.ic_access_alarm, newTime.toString(), "skuska", false));
                recAdap.notifyDataSetChanged();

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

//    private void setStartAlarm(ArrayList<Date> times) {
////        Date dateFrom, Date timeOnly
////        datetime, time
//        for(Date timeOnly: times) {
//            Date tmpDate = new Date(
//                    datetime.getYear(),
//                    datetime.getMonth(),
//                    datetime.getDate(),
//                    timeOnly.getHours(),
//                    timeOnly.getMinutes(),
//                    timeOnly.getSeconds()
//            );
//            long alarmStartTime = tmpDate.getTime();
//            long alarmExecuteInterval = 24 * 60 * 60 * 1000;
//
//            for (int i = 0; i < times.size(); ++i) {
//                Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
//                // Loop counter `i` is used as a `requestCode`
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//                // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
//                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);
//
//                intentArray.add(pendingIntent);
//                Toast.makeText(getApplicationContext(),
//                        "Alarm bol nastavený!",
//                        Toast.LENGTH_LONG
//                ).show();
//
//            }
//        }
////        Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
////        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
////                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        //TODO pre kazdy alarm treba vytvorit unikatne id
//        //pendingIntents.put("FIRST_ALARM", pendingIntent);
//
//
////        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);
////
////        Toast.makeText(getApplicationContext(),
////                "Za minutu budem zvonit",
////                Toast.LENGTH_LONG
////        ).show();
//    }
private void setStartAlarm(ArrayList<Date> times) {

    for (int i = 0; i < times.size(); ++i) {

        Date tmpDate = new Date(
                datetime.getYear(),
                datetime.getMonth(),
                datetime.getDate(),
                times.get(i).getHours(),
                times.get(i).getMinutes(),
                times.get(i).getSeconds()
        );
        long alarmStartTime = tmpDate.getTime();
        long alarmExecuteInterval = 24 * 60 * 60 * 1000;

        Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
        // Loop counter i is used as a requestCode
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Single alarms in 1, 2, ..., 10 minutes (in i minutes)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);

        intentArray.add(pendingIntent);
        Toast.makeText(getApplicationContext(),
                "Alarm bol nastavený!",
                Toast.LENGTH_LONG
        ).show();
    }
}
}

