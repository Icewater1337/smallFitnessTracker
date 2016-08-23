package icewater.mytrackingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        String foodString = intent.getStringExtra("foodString");
        String trainingString = intent.getStringExtra("trainingString");

        String[] foodsList = foodString.split("\\|");
        String[] trainingList = trainingString.split("\\|");
        String[] list = new String[foodsList.length+trainingList.length+1];
        if ( !(foodsList[0].equals("") && trainingList[0].equals(""))) {
            for ( int i = 0; i < foodsList.length; i++) {
                list[i] = foodsList[i];
            }
            for ( int i = 0; i < trainingList.length; i++) {
                list[foodsList.length] = "Trainings";
                list[i+foodsList.length+1] = trainingList[i];
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view , list);
            ListView foodList = (ListView) findViewById(R.id.listFood);

            foodList.setAdapter(adapter);
        } else {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_view);
            ListView foodList = (ListView) findViewById(R.id.listFood);

            foodList.setAdapter(adapter);
            adapter.add("no entries for today");
        }





    }
}
