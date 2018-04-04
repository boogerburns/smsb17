package com.myshop.booger.text;

import android.Manifest;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.telephony.gsm.SmsManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final TextView phonenumber = (TextView) findViewById(R.id.phonenumber);
        final TextView message = (TextView) findViewById(R.id.message);
        final TextView textnumber = (TextView) findViewById(R.id.textnumber);

        phonenumber.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                phonenumber.setText("");
            }
        });

        message.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                message.setText("");
            }
        });

        textnumber.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                textnumber.setText("");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phonenumber.getText().toString();
                String messageString = message.getText().toString();
                int numberoftexts = Integer.parseInt(textnumber.getText().toString());
                sendSMS(phoneNumber,messageString, numberoftexts);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
