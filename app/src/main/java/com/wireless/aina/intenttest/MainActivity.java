package com.wireless.aina.intenttest;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


/**
 * In this example we register a {@link BroadcastReceiver} which listens for Aina Connected -specific
 * intents which contain button status actions. This is the easies way to let an application to
 * act based on the button presses on the device since there is no need to bind the service.
 *
 */
public class MainActivity extends AppCompatActivity {
    // our broadcast receiver, which receives updates regarding the ACTIONS
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // retrieve the intent's values
            String action = intent.getAction();

            // Aina Wireless' intents have a field "deviceName" which contains the name of the
            // device that generated the broadcast
            String device = intent.getStringExtra("deviceName");

            // update UI
            showBroadcast(action, device);
        }
    };

    // ui components
    private TextView intentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentText = findViewById(R.id.intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // construct an IntentFilter which informs Android about the intent's we'd like to receive
        IntentFilter filter = new IntentFilter();

        filter.addAction("com.ainawireless.intent.action.PTT1_DOWN");
        filter.addAction("com.ainawireless.intent.action.PTT1_UP");
        filter.addAction("com.ainawireless.intent.action.PTT2_DOWN");
        filter.addAction("com.ainawireless.intent.action.PTT2_UP");
        filter.addAction("com.ainawireless.intent.action.EMERG_DOWN");
        filter.addAction("com.ainawireless.intent.action.EMERG_UP");
        filter.addAction("com.ainawireless.intent.action.BTN1_DOWN");
        filter.addAction("com.ainawireless.intent.action.BTN1_UP");
        filter.addAction("com.ainawireless.intent.action.BTN2_DOWN");
        filter.addAction("com.ainawireless.intent.action.BTN2_UP");
        filter.addAction("com.ainawireless.intent.action.BTN3_DOWN");
        filter.addAction("com.ainawireless.intent.action.BTN3_UP");
        filter.addAction("com.ainawireless.intent.action.BTN4_DOWN");
        filter.addAction("com.ainawireless.intent.action.BTN4_UP");

        // register our BroadcastReceiver with the filter
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister our BroadcastReceiver
        this.unregisterReceiver(receiver);
    }

    /**
     * Updates our UI with the information received through our BroadcastReceiver
     * @param action    Name of the action
     * @param device    Name of the source device
     */
    private void showBroadcast(String action, String device) {
        // retrieve the button code from the action string (last component)
        String[] actionComponents = action.split("\\.");
        String button = actionComponents[actionComponents.length-1];

        // display our event on UI
        intentText.setText(String.format(
                getString(R.string.broadcasts_received),
                button,
                device
        ));
    }
}