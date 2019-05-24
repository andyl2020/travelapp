package com.example.firsttestapp;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "example.txt";

    EditText mEditText;
    RadioGroup radioGroupRoute;
    RadioButton radioButtonRouteChoice;
    RadioGroup radioGroupTraffic;
    RadioButton radioButtonTrafficChoice;
    Button buttonSendText;
    Switch switchMobileData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);
        radioGroupRoute = findViewById(R.id.radioGroup_Route);
        radioGroupTraffic = findViewById(R.id.radioGroup_traffic);
        buttonSendText = findViewById(R.id.button_sendText);
        switchMobileData = findViewById(R.id.switch_mobileData);

        //buttonSendText.setEnabled(false);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.CHANGE_NETWORK_STATE}, 1);

    }


    public void toggleMobileData(View v) {
        boolean switchState = switchMobileData.isChecked();
        String switchStateStr = String.valueOf(switchState);
        Toast.makeText(this, "Data on? " + switchStateStr, Toast.LENGTH_SHORT).show();
        toggleMobileDataConnection(switchState);
    }


    public boolean toggleMobileDataConnection(boolean ON)
    {
        try {
            //create instance of connectivity manager and get system connectivity service
            final ConnectivityManager conman = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            //create instance of class and get name of connectivity manager system service class
            final Class conmanClass  = Class.forName(conman.getClass().getName());
            //create instance of field and get mService Declared field
            final Field iConnectivityManagerField= conmanClass.getDeclaredField("mService");
            //Attempt to set the value of the accessible flag to true
            iConnectivityManagerField.setAccessible(true);
            //create instance of object and get the value of field conman
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            //create instance of class and get the name of iConnectivityManager field
            final Class iConnectivityManagerClass=  Class.forName(iConnectivityManager.getClass().getName());
            //create instance of method and get declared method and type
            final Method setMobileDataEnabledMethod= iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled",Boolean.TYPE);
            //Attempt to set the value of the accessible flag to true
            setMobileDataEnabledMethod.setAccessible(true);
            //dynamically invoke the iConnectivityManager object according to your need (true/false)
            setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);
        } catch (Exception e){
        }
        return true;
    }

    public void textMom(View v) {
        String phoneNumber = "7789960898";
        String smsMessage = "here";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);
        Toast.makeText(this, "Message sent to Mom!",
                Toast.LENGTH_SHORT).show();
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





