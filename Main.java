package com.example.sms;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

    Button sendSMS;
    EditText msgTxt;
    EditText numTxt;
    IntentFilter intentFilter;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView inTxt = findViewById(R.id.textMsg);
            inTxt.setText(intent.getExtras().getString("sms"));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        sendSMS = findViewById(R.id.sendButton);
        msgTxt = findViewById(R.id.editText2);
        numTxt = findViewById(R.id.editText);
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myMsg = msgTxt.getText().toString();
                String theNum = numTxt.getText().toString();
                sendMsg(theNum, myMsg);
            }
        });
    }

    protected void sendMsg(String theNum, String myMsg) {
        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                  case Activity.RESULT_OK:
                      Toast.makeText(Main.this, "SMS sent", Toast.LENGTH_LONG).show();
                      break;
                  case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                      Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_LONG).show();
                      break;
                  case SmsManager.RESULT_ERROR_NO_SERVICE:
                      Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_LONG).show();
                      break;
              }
          }
      }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(theNum, null, myMsg, sentPI, deliveredPI);
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }
    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }
}
