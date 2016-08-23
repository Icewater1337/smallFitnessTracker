package icewater.mytrackingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

public class HistoryCalendar extends AppCompatActivity {
    String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

       CalendarView calendar = ( CalendarView) findViewById(R.id.historyCalendar);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
               String monthString ="";

               if ( month <10) {
                   monthString = "0"+String.valueOf(month+1);
               } else {
                   monthString = String.valueOf(month+1);
               }
                selectedDate =String.valueOf(day)+"/"+monthString+"/"+String.valueOf(year);

                SharedPreferences sp = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);


                String foodStringKey = selectedDate+"Food";
                String trainingStringKey = selectedDate+"Training";
                String foodStringValue = "";
                foodStringValue =getFoodString(foodStringKey);
                String trainingStringValue = "";
                trainingStringValue = getTrainingString(trainingStringKey);

                Intent intent = new Intent(HistoryCalendar.this, History.class);
                intent.putExtra("foodString",foodStringValue);
                intent.putExtra("trainingString", trainingStringValue);
                startActivity(intent);
            }
        });
        }

    public String getTrainingString(String dayTrainingStringName) {

        String currentTrainingString="";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        currentTrainingString   = spD.getString(dayTrainingStringName, currentTrainingString);
        return currentTrainingString;
    }

    public String getFoodString(String dayFoodStringName) {
        String currentFoodString="";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        currentFoodString   = spD.getString(dayFoodStringName, currentFoodString);
        return currentFoodString;
    }
}
