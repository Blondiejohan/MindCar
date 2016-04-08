package mindcar.testing.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mindcar.testing.objects.*;
import mindcar.testing.*;

import java.util.ArrayList;


/**
 * Created by Johan And Sarah.
 */

public class Bluetooth extends Activity {

    private static final int DISCOVERY_REQUEST = 1;
    public Button connect;
    public ArrayList<BluetoothDevice> newDevList;
    public ArrayList<BluetoothDevice> pairDevList;
    public ProgressBar loading;
    public TextView text;
    // we are going to call this when we the activity is created
    private BluetoothAdapter theAdapter;// this class intitilizes bt hardware on a device


    // Tries to get the defaultadapter, if bluetooth is on, it starts the connecting procedure.
    // if its not on display the connect button.
    // create a broadCast Receiver that gets called when new devices are found.
    // If a new device is found it puts it into the newdevarray and calls verify.
    // If a paired device is found it puts it into the pairdevarray and calls verify.
    protected BroadcastReceiver bondedResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            BluetoothDevice bondDevice;
            bondDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (theAdapter.getBondedDevices().contains(bondDevice)) {
                Log.i("paired device added", bondDevice.getName());

                if (newDevList.contains(bondDevice)) {
                    newDevList.remove(bondDevice);
                }
                if (!pairDevList.contains(bondDevice)) {
                    pairDevList.add(bondDevice);
                }
                verify();
            } else {
                Log.i("new device added", bondDevice.getName());
                if (!newDevList.contains(bondDevice)) {
                    newDevList.add(bondDevice);
                }
                if (pairDevList.contains(bondDevice)) {
                    pairDevList.remove(bondDevice);
                    verify();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        theAdapter = BluetoothAdapter.getDefaultAdapter();
        newDevList = new ArrayList<>();
        pairDevList = new ArrayList<>();
        setupUI();
        if (theAdapter.isEnabled()) {
            String deviceDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE; //makes us discoverable
            startActivityForResult(new Intent(deviceDiscoverable), DISCOVERY_REQUEST);
            text.setText("Searching for car");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    verify();
                }
            }, 5000);

            connect.setVisibility(View.GONE);
        } else {

            connect.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }

    }

    // Sets up the button, text and loading icon. Also sets the onclicklisteners.
    public void setupUI() {
        connect = (Button) findViewById(R.id.connect);
        connect.setVisibility(View.GONE);
        loading = (ProgressBar) findViewById(R.id.progressBar);
        loading.setVisibility(View.VISIBLE);
        text = (TextView) findViewById(R.id.text);

        // Click this to start looking for new devices, starts an activifyforresult that is discovery.
        // Register a reciever thats listens for new devices connecting
        // updates the text and switches the connect button for the loading icon.
        // If the correct devices isn't found withing 5 seconds it calls verify.
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), DISCOVERY_REQUEST);
                registerReceiver(bondedResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
                text.setText("Searching for car");
                connect.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        verify();
                    }
                }, 5000);


            }
        }); // end connect
    }

    // This is the method that gets called when the startactivityforresult find a result.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DISCOVERY_REQUEST) {
            findDevices();
        }
    }

    // Sets a reciever that reacts to all the devices found.
    public void findDevices() { //check for devices
        if (theAdapter.startDiscovery()) {
            registerReceiver(bondedResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    // This gets called every time a new device is either put into the new or paired list.7
    // Its checks if the device is the ones we want it to connect to, "car" and "headset".
    // If both of the devices are connected then go on to the next screen, if not then do nothing.
    // Or if both of the lists are empty display that you need to maybe start the device and scan again.
    public void verify() {

        if (pairDevList.size() > 0) {
            for (BluetoothDevice verDev : pairDevList) {
                Log.i("found paired device ", verDev.getName());
                //if (verDev.getName() == " BT-SPEAKER ") {
                Connection conn = new Connection(verDev);
                conn.start();
                text.setText("Move to next screen");
                loading.setVisibility(View.GONE);
                //}
            }


        } else if (newDevList.size() > 0) {
            for (final BluetoothDevice verDev : newDevList) {
                Log.i("found new device ", verDev.getName());
                //if (verDev.getName()==" BT-SPEAKER "){
                Log.i("matched new ", verDev.getName());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    verDev.createBond();
                }
                Log.i("bondCreated", verDev.getName());

                registerReceiver(bondedResult, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
                text.setText("Found car, Attemting to pair");
                //}
            }

        } else {
            connect.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            text.setText("Try turning on the car then scan again");


        }
    }
}