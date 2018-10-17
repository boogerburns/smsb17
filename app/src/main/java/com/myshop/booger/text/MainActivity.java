package com.myshop.booger.text;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final TextView phonenumber = getPhoneTextView();
        final TextView message = getBodyTextView();
        final TextView textnumber = getTextNumberTextView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfReadyToSend(phonenumber.getText().toString(),message.getText().toString(),textnumber.getText().toString())) {
                    try {
                        sendSMS(phonenumber.getText().toString(), message.getText().toString(), Integer.parseInt(textnumber.getText().toString()));
                    } catch (Exception e) {
                        System.out.println("Error :" + e.getMessage());
                    }
                }
                else{
                    Log.i("checkIfReadyToSend","Not ready to send text");
                    Snackbar messagewindow = Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "", 2);
                    messagewindow.setText("Not ready to send texts check your inputs");
                    messagewindow.setDuration(Snackbar.LENGTH_LONG);
                    messagewindow.show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Log.i("action_settings","Settings on click");
            Snackbar messagewindow = Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "", 2);
            messagewindow.setText("Settings has not been implemented yet");
            messagewindow.setDuration(Snackbar.LENGTH_LONG);
            messagewindow.show();
        }

        if (id == R.id.action_clear_all){
            clear_all();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clear_all(){
        TextView phoneView=getPhoneTextView();
        TextView bodyView=getBodyTextView();
        TextView textNumberView=getTextNumberTextView();

        phoneView.setText(null);
        bodyView.setText(null);
        textNumberView.setText(null);

    }

    private TextView getPhoneTextView(){
       return (TextView) findViewById(R.id.phonenumber);
    }

    private TextView getBodyTextView(){
        return (TextView) findViewById(R.id.message);
    }

    private TextView getTextNumberTextView(){
        return (TextView) findViewById(R.id.textnumber);
    }


    //Sends an SMS message to another device

    private void sendSMS(String phoneNumber, String message, int numberoftexts) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        SmsManager sms = SmsManager.getDefault();
        for(int i=0; i< numberoftexts; i++) {
            try {
                sms.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("semdSMS","Sent " + i + " messages");
                Snackbar messagewindow = Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "", 2);
                messagewindow.setText("Message Success" + " " + phoneNumber + " " + message);
                messagewindow.setDuration(Snackbar.LENGTH_LONG);
                messagewindow.show();
            } catch (Exception e) {
                Log.e("sendSMS", e.toString());
                Snackbar messagewindow = Snackbar.make(findViewById(R.id.mainCoordinatorLayout), "", 2);
                messagewindow.setText("Message failure" + " " + e.toString());
                messagewindow.setDuration(Snackbar.LENGTH_INDEFINITE);
                messagewindow.show();
            }
        }
    }

    private Boolean checkIfReadyToSend(String phoneNumber, String body, String numberOfTexts) {
        Boolean ready;
        if (TextUtils.isEmpty(phoneNumber)){
            ready=Boolean.FALSE;
        }
        else if (TextUtils.isEmpty(body)){
            ready=Boolean.FALSE;
        }
        else if (TextUtils.isEmpty(numberOfTexts)){
            ready=Boolean.FALSE;
        }
        else{
            ready=Boolean.TRUE;
        }
        return ready;
    }
}
