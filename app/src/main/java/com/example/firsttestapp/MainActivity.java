package com.example.firsttestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "example.txt";

    EditText mEditText;
    ToggleButton toggleDate; //T5
    ToggleButton toggleT1; //T1
    ToggleButton toggleT2; //T2
    ToggleButton toggleT4; //T4
    ToggleButton toggleT5; //T5
    ToggleButton toggleR1;
    ToggleButton toggleR2;
    ToggleButton toggleR3;
    RadioGroup radioGroupRoute;
    RadioButton radioButtonRouteChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);
        toggleT1 = findViewById(R.id.toggleButtonT1);
        toggleT2 = findViewById(R.id.toggleButtonT2);
        toggleDate = findViewById(R.id.toggleButtonDate);
        toggleT4 = findViewById(R.id.toggleButtonT4);
        toggleT5 = findViewById(R.id.toggleButtonT5);
        toggleR1 = findViewById(R.id.toggleButtonR1);
        toggleR2 = findViewById(R.id.toggleButtonR2);
        toggleR3 = findViewById(R.id.toggleButtonR3);
        radioGroupRoute = findViewById(R.id.radioGroup_Route);

    }

    public void clear(View v) {
        //String text = mEditText.getText().toString();
        String text = "";
        FileOutputStream fos = null;

        try {
            //private mode only clears whatever is on the txt file
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            text = text + "\r\n";
            fos.write(text.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void printTrafficAndRouteInfo(View v) {
        //String text2 = currentTime.toString();
        //String text = "Hi"+"\r\n";
        String trafficRG = "";
        String traffic = "";
        FileOutputStream fos = null;

        int selectedId = radioGroupRoute.getCheckedRadioButtonId();
        radioButtonRouteChoice = (RadioButton)findViewById(selectedId);
        trafficRG = radioButtonRouteChoice.getText().toString();

        if (toggleDate.isChecked()){
            //text = "DATE SAVED: "+text2+"\r\n";
            traffic = "T3,";
        }
        else if (toggleT1.isChecked()){
            traffic = "T1,";
        }
        else if (toggleT2.isChecked()){
            traffic = "T2,";
        }
        else if (toggleT4.isChecked()){
            traffic = "T4,";
        }
        else if (toggleT5.isChecked()){
            traffic = "T5,";
        }

        if (toggleR2.isChecked()){
            //text = "DATE SAVED: "+text2+"\r\n";
            traffic = traffic + "Alex Fraser bridge";
        }
        else if (toggleR1.isChecked()){
            traffic = traffic + "Grand Tunnel";
        }
        else if (toggleR3.isChecked()){
            traffic = traffic + "Port mann Bridge";
        }

        traffic = traffic + " RG: " + trafficRG + "\r\n";

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE | MODE_APPEND);
            fos.write(traffic.getBytes());
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void printTimeDate(View v) {
        Date currentTime = Calendar.getInstance().getTime();
        String text2 = currentTime.toString() + ",";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE | MODE_APPEND);
            fos.write(text2.getBytes());
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}