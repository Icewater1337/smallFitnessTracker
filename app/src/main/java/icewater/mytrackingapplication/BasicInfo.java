package icewater.mytrackingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BasicInfo extends AppCompatActivity {

    private String age,height,weight,bfp, dailyCalorieUsage, rmr,cpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Set your basic info");

        setContentView(R.layout.activity_basic_info);
        Intent intent = getIntent();
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        // load already there values and set them

        EditText ageF =(EditText) findViewById(R.id.age);
        EditText heightF =((EditText) findViewById(R.id.height));
        EditText weightF =((EditText) findViewById(R.id.weight));
        EditText bfpF = (EditText) findViewById(R.id.bfp);
        EditText rmrF = (EditText) findViewById(R.id.editRmr);
        EditText cpmF = (EditText) findViewById(R.id.bulkCutNmbr);

        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        ageF.setText(sp.getString("age", age));
        heightF.setText(sp.getString("height", height));
        weightF.setText(sp.getString("weight", weight));
        bfpF.setText(sp.getString("bfp", bfp));
        rmrF.setText(sp.getString("dailyCalorieUsage",dailyCalorieUsage));
        cpm = sp.getString("caloriePlusMinus", cpm);
        cpmF.setText(cpm);



        Spinner spinner = (Spinner) findViewById(R.id.bulkCut);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);


        spinner.setAdapter(adapter);

        if(cpm != null) {
        if ( cpm.contains("+")) {
            adapter.add("Bulk");
            adapter.add("Cut");
        } else if ( cpm.contains("-")) {
            adapter.add("Cut");
            adapter.add("Bulk");
        }
        else{
            adapter.add("Bulk");
            adapter.add("Cut");
        }} else{
            adapter.add("Bulk");
            adapter.add("Cut");
        }

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        findViewById(R.id.bulkCutNmbr).setEnabled(true);
                        if (parent.getSelectedItem().toString().toLowerCase().contains("bulk") && cpm == null) {
                            ((EditText)findViewById(R.id.bulkCutNmbr)).setText("+10%");
                        } else if ( cpm == null)
                            ((EditText)findViewById(R.id.bulkCutNmbr)).setText("-20%");


                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        System.out.println("");
                    }
                });






    }

    public void calculateBase(View view) {
        Double age = Double.parseDouble(((EditText) findViewById(R.id.age)).getText().toString());
        Double height =Double.parseDouble( ((EditText) findViewById(R.id.height)).getText().toString());
        Double weight = Double.parseDouble(((EditText) findViewById(R.id.weight)).getText().toString());
        Double bfp = Double.parseDouble(((EditText) findViewById(R.id.bfp)).getText().toString());

        Spinner spinner = (Spinner) findViewById(R.id.calculationsDropdown);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        ((EditText)findViewById(R.id.editRmr)).setText(parent.getSelectedItem().toString());

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        Double lmb = weight - (weight * (bfp/100));
        Double miffinDbl = (10*weight)+(6.25*height)-(4.92*age)+5;
        Double harrisDbl = 88.362+ (13.397 *weight)+ (4.4799 * height)-(5.677* age);
        Double alanDbl = 25.3 * lmb;

       findViewById(R.id.editRmr).setEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.add("Choose calculation");
        adapter.add(String.valueOf(miffinDbl));
        adapter.add(String.valueOf(harrisDbl));
        adapter.add(String.valueOf(alanDbl));



    }

    public void saveBaseInfo(View view) throws IOException {
         dailyCalorieUsage =((EditText) findViewById(R.id.editRmr)).getText().toString();
         age =((EditText) findViewById(R.id.age)).getText().toString();
         height = ((EditText) findViewById(R.id.height)).getText().toString();
         weight = ((EditText) findViewById(R.id.weight)).getText().toString();
         bfp = ((EditText) findViewById(R.id.bfp)).getText().toString();
        cpm = ((EditText)findViewById(R.id.bulkCutNmbr)).getText().toString();
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("dailyCalorieUsage",dailyCalorieUsage);
        editor.putString("age",age);
        editor.putString("height",height);
        editor.putString("weight",weight);
        editor.putString("bfp",bfp);
        editor.putString("caloriePlusMinus", cpm);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);





    }
}
