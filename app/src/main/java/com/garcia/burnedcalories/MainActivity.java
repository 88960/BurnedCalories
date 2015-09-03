package com.garcia.burnedcalories;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnKeyListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity implements OnEditorActionListener, OnSeekBarChangeListener, OnKeyListener
{
    //declare instance variables
   private EditText weightEditText;
    private TextView milesTextView;
    private SeekBar milesSeekBar;
    private TextView caloriesTextView;
    private Spinner inchesSpinner;
    private Spinner feetSpinner;
    private TextView bmiTextView;
    private EditText nameEditText;

    ArrayAdapter<CharSequence> feetAdapter;
    ArrayAdapter<CharSequence> inchesAdapter;

    // define instance variables that should be saved
    private String weightString = "";
    private float tipPercent = .15f;
    private SharedPreferences savedValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to the widgets
        weightEditText = (EditText)findViewById(R.id.weightEditText);
        milesTextView = (TextView)findViewById(R.id.milesTextView);
        milesSeekBar = (SeekBar)findViewById(R.id.milesSeekBar);
        caloriesTextView = (TextView)findViewById(R.id.caloriesTextView);
        feetSpinner = (Spinner)findViewById(R.id.feetSpinner);
        inchesSpinner = (Spinner)findViewById(R.id.inchesSpinner);
        bmiTextView = (TextView)findViewById(R.id.bmiTextView);
        nameEditText = (EditText)findViewById(R.id.nameEditText);

        //Spinner Adapters
        feetAdapter = ArrayAdapter.createFromResource(this, R.array.feet, android.R.layout.simple_spinner_item);
        inchesAdapter = ArrayAdapter.createFromResource(this, R.array.inches, android.R.layout.simple_spinner_item);

        //set the values for the height spinner
/*
        feetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feetSpinner.setAdapter(feetAdapter);
       // feetSpinner.setOnItemSelectedListener(new);

        inchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inchesSpinner.setAdapter(inchesAdapter);
     /*   inchesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                                {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                        Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                }*/


        //set the listeners
        weightEditText.setOnEditorActionListener(this);
        milesSeekBar.setOnSeekBarChangeListener(this);
        milesSeekBar.setOnKeyListener(this);
    }

    @Override
    public void onPause()
    {
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("WeightString", weightString);
        editor.putFloat("", tipPercent);
        editor.commit();

        super.onPause();
    }

   @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
//        weightString = savedValues.getString("weightString", "");
        int progress = milesSeekBar.getProgress();
        milesSeekBar.setProgress(progress);

        // tipPercent = savedValues.getFloat("tipPercent", 0.15f);

        // set the bill amount on its widget
        weightEditText.setText(weightString);

        // calculate and display
        calculateAndDisplay();
    }
    public void calculateAndDisplay() {



        // get the bill amount
        weightString = weightEditText.getText().toString();

        float weight;
        if (weightString.equals("")) {
            weight = 0;
        }
        else {
            weight = Float.parseFloat(weightString);
        }

        //get tip percent
        int miles =  milesSeekBar.getProgress();
       //int inches = (Integer) inchesSpinner.getSelectedItem();
  //      int feet = (Integer)feetSpinner.getSelectedItem();


        // calculate tip and total
        double caloriesBurned = 0.75 * weight * miles;
        //double bmi = (weight * 703) / ((12 * feet + inches) * (12 * feet + inches));

        // display the other results with formatting
        NumberFormat number = NumberFormat.getNumberInstance();
        caloriesTextView.setText(number.format(caloriesBurned)+ " cal / mile");
      //  bmiTextView.setText(currency.format(bmi));




        NumberFormat percent = NumberFormat.getPercentInstance();
       // percentTextView.setText(percent.format(tipPercent));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

    // event handlers for seekBar
    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        milesTextView.setText(progress + "mph");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        calculateAndDisplay();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }
}
