package icewater.mytrackingapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Icewater on 22.08.2016.
 */
public class DailyBusinessHandler {

    String currentApplicationDate;

    public void createNewDay(SharedPreferences sp) {
        currentApplicationDate = MainActivity.getCurrentApplicationDate();
        SharedPreferences spD = sp;
        String dayFoodStringName = currentApplicationDate+"Food";
        String dayTrainingStringName = currentApplicationDate+"Training";

        String dayFoodString= "";
        String dayTrainingString ="";

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(dayFoodStringName, dayFoodString);
        editor.putString(dayTrainingStringName, dayTrainingString);
        editor.commit();
    }

    public Boolean checkIfSameDay() {
        currentApplicationDate = MainActivity.getCurrentApplicationDate();
        String todaysDate = getTodaysDate();

        if(todaysDate.equals(currentApplicationDate)) {
          return true;
        }
        return false;

    }

    public static String getTodaysDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
