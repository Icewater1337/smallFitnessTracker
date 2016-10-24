package icewater.mytrackingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String cpm;
    private String rmr;
    private String weightString;
    public static String currentApplicationDate;
    public DailyBusinessHandler dbh;


    public static String getCurrentApplicationDate() {
        return currentApplicationDate;
    }

    public static void setApplicationDate(String date){
        currentApplicationDate = date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(dbh == null) {
            dbh = new DailyBusinessHandler();
        }



        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);

        currentApplicationDate = spD.getString("currentApplicationDate", currentApplicationDate);

        if( currentApplicationDate == null) {
            currentApplicationDate = DailyBusinessHandler.getTodaysDate();

            SharedPreferences.Editor editor = spD.edit();
            editor.putString("currentApplicationDate", currentApplicationDate);
            editor.commit();

        }

        weightString = spD.getString("weightString", weightString);

        if( weightString == null) {
            weightString ="";
            SharedPreferences.Editor  edit = spD.edit();
            edit.putString("weightString", weightString);
            edit.commit();

        }

        if( !dbh.checkIfSameDay()) {
            dbh.createNewDay(spD);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddStuff.class);
                startActivity(intent);
            }
        });


        try {

            TextView dailyNeed = (TextView) findViewById(R.id.dailyNeed);
            TextView rmr10 = (TextView)findViewById(R.id.rmr10);

            SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            cpm = sp.getString("caloriePlusMinus", cpm);
            rmr =  sp.getString("dailyCalorieUsage",rmr);
            Double rmrPlus10 = Double.parseDouble(rmr)*1.2;
            rmr10.setText(String.valueOf(rmrPlus10));
            Double cpmPerc = Double.valueOf(0);
            if (cpm.contains("+")) {
                cpm = cpm.replace("+", "").replace("%", "");
                cpmPerc = 1 + Double.parseDouble(cpm) / 100;
            } else if (cpm.contains("-")) {
                cpm = cpm.replace("-", "").replace("%", "");
                cpmPerc = 1 - Double.parseDouble(cpm) / 100;
            }
            String dailyNeedString = String.valueOf((Double.parseDouble(((TextView) findViewById(R.id.rmr10)).getText().toString()) +Double.valueOf(getTodaysTrainingCalories()))* cpmPerc);
            dailyNeed.setText(dailyNeedString);

            // still needed calories:
            TextView stillNeeded = (TextView) findViewById(R.id.stillNeeded);

            Double alreadyUsedCalories = Double.valueOf(getTodaysFoodCalories());
            String dailyNeedLeft = String.valueOf(Double.valueOf(dailyNeedString) - alreadyUsedCalories);

            stillNeeded.setText(dailyNeedLeft);

        } catch (Exception d){}

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

    public String getTodaysFoodCalories() {
        String currentFoodString = getTodaysFoodString();
        String[] dailyFoodCalories = currentFoodString.split("\\|");
        Double dailyFoodCaloriesNmbr = 0.0;
        for (int i = 0; i < dailyFoodCalories.length; i++) {
           dailyFoodCaloriesNmbr += Double.valueOf( dailyFoodCalories[i].split("c:")[1]);

        }
        return String.valueOf(dailyFoodCaloriesNmbr);

    }

    public String getTodaysTrainingCalories() {
        String currentTrainingString = getTodaysTrainingString();
        String[] dailyTrainingCalories = currentTrainingString.split("\\|");
        Double dailyTrainingCaloriesNmbr = 0.0;
        if(!dailyTrainingCalories[0].equals("")) {
            for (int i = 0; i < dailyTrainingCalories.length; i++) {
                dailyTrainingCaloriesNmbr += Double.valueOf(dailyTrainingCalories[i].split("c:")[1]);

            }
        }
        return String.valueOf(dailyTrainingCaloriesNmbr);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_basicInfo) {
            Intent intent = new Intent(this, BasicInfo.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, HistoryCalendar.class);
            startActivity(intent);

        }  else if (id == R.id.nav_weight) {
        Intent intent = new Intent(this, WeightHistory.class);
        startActivity(intent);

    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saveWeight(View view) {
        String dailyWeight = ((EditText)findViewById(R.id.dailyWeight)).getText().toString();
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("weight",dailyWeight);
        editor.commit();
        dailyWeight = currentApplicationDate+":"+dailyWeight+"|";
        SharedPreferences spD = getSharedPreferences("DailyBusiness", Context.MODE_PRIVATE);
        weightString = spD.getString("weightString", weightString);
        weightString += dailyWeight;
        SharedPreferences.Editor  edit = spD.edit();
        edit.putString("weightString", weightString);
        edit.commit();

        // adjust weight in basic infos and recalculate stuff



    }
}
