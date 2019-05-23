package com.example.firsttestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    RadioGroup radioGroupRoute;
    RadioButton radioButtonRouteChoice;
    RadioGroup radioGroupTraffic;
    RadioButton radioButtonTrafficChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);
        radioGroupRoute = findViewById(R.id.radioGroup_Route);
        radioGroupTraffic = findViewById(R.id.radioGroup_traffic);

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
        String routeChoiceStr;
        String trafficChoiceStr;
        String trafficAndRouteChoice;
        FileOutputStream fos = null;

        int radioButtonRouteChoiceId = radioGroupRoute.getCheckedRadioButtonId();
        radioButtonRouteChoice = (RadioButton)findViewById(radioButtonRouteChoiceId);
        routeChoiceStr = radioButtonRouteChoice.getText().toString();

        int radioButtonTrafficChoiceId = radioGroupTraffic.getCheckedRadioButtonId();
        radioButtonTrafficChoice = (RadioButton)findViewById(radioButtonTrafficChoiceId);
        trafficChoiceStr = radioButtonTrafficChoice.getText().toString();

        trafficAndRouteChoice = trafficChoiceStr + "," + routeChoiceStr + "\r\n";

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE | MODE_APPEND);
            fos.write(trafficAndRouteChoice.getBytes());
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