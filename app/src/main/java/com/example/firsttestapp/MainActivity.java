package com.example.firsttestapp;

import android.Manifest;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
    boolean stopwatchOn = false;

    EditText mEditText;
    RadioGroup radioGroupRoute;
    RadioButton radioButtonRouteChoice;
    RadioGroup radioGroupTraffic;
    RadioButton radioButtonTrafficChoice;
    Button buttonSendText;
    TextView displayStartTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);
        radioGroupRoute = findViewById(R.id.radioGroup_Route);
        radioGroupTraffic = findViewById(R.id.radioGroup_traffic);
        buttonSendText = findViewById(R.id.button_sendText);
        displayStartTime = findViewById(R.id.textview_time1);

        //buttonSendText.setEnabled(false);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

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
        if (!stopwatchOn) {
            long timeMilliSec = currentTime.getTime();
            displayStartTime.setText(String.valueOf(timeMilliSec));
            stopwatchOn = true;
        }
        else if (stopwatchOn) {
            long timeMilliSecStart = Long.parseLong(displayStartTime.getText().toString());
            long timeMilliSecStop = currentTime.getTime();
            double timeMinuteDiff = ((double)(timeMilliSecStop - timeMilliSecStart)/1000)/60;
            String s = String.format("%.2f", timeMinuteDiff)+"min";
            displayStartTime.setText(s);
            stopwatchOn = false;
        }
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


    // volume controls

    public void muteAudio(){
        AudioManager mAlramMAnager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }
    }

    public void unMuteAudio(){
        AudioManager mAlramMAnager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }
    }




    //EOF
}