package icewater.mytrackingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddStuff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);
        setTitle("Add a training or food");
    }


    public void addTraining(View view) {
        String name = ((EditText)findViewById(R.id.trainingName)).getText().toString();
        String calories = ((EditText)findViewById(R.id.caloriesBurned)).getText().toString();
        String currentFoodString=getTodaysTrainingString();
        String dayTrainingStringName = MainActivity.getCurrentApplicationDate()+"Training";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        currentFoodString += "n:"+name+"c:"+calories+"|";

        SharedPreferences.Editor editor = spD.edit();
        editor.putString(dayTrainingStringName,currentFoodString);
        editor.commit();
    }

    public void addFood(View view) {
        String name = ((EditText)findViewById(R.id.foodName)).getText().toString();
        String calories = ((EditText)findViewById(R.id.foodCalories)).getText().toString();
        String currentFoodString=getTodaysFoodString();
        String dayFoodStringName = MainActivity.getCurrentApplicationDate()+"Food";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        currentFoodString += "n:"+name+"/c:"+calories+"|";

        SharedPreferences.Editor editor = spD.edit();
        editor.putString(dayFoodStringName,currentFoodString);
        editor.commit();


    }

    public String getTodaysTrainingString() {

        String currentTrainingString="";
        String dayTrainingStringName = MainActivity.getCurrentApplicationDate()+"Training";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        currentTrainingString   = spD.getString(dayTrainingStringName, currentTrainingString);
        return currentTrainingString;
    }

    public String getTodaysFoodString() {
        String currentFoodString="";
        String dayFoodStringName = MainActivity.getCurrentApplicationDate()+"Food";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        currentFoodString   = spD.getString(dayFoodStringName, currentFoodString);
        return currentFoodString;
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
