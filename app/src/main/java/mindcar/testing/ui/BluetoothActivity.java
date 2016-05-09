package mindcar.testing.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import mindcar.testing.R;



/**
 * Created by Sarah And Johan, refactored and commented by Sarah on 2016-05-01.
 * This class starts the bluetooth adapter, then pairs and connects the adapter to Nemesis system.
 */

public class BluetoothActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    protected static final int SUCCESS_CONNECT = 0;//Handler for situation of connection status
    protected static final int MESSAGE_READ = 1;//Handler for situation of connection status
    private Button exit; // terminates the application
    private Button activate;// turns Bluetooth on and initiates methods
    private ListView listView;//shows paired items
    private ProgressDialog mProgressDlg;//for discovery progress
    private ProgressBar bar;// waiting indicator for pairing and connecting
    private BluetoothAdapter theAdapter = BluetoothAdapter.getDefaultAdapter(); //the bluetooth adapter
    private Set<BluetoothDevice> paired_devices; //a set of bonded devices
    private ArrayAdapter<String> mylist; //viewed in the list view, the paired devices
    private ArrayList<BluetoothDevice> mDeviceList;// discovery list for adding requiered devices
    private IntentFilter filter; // an intent
    private boolean deviceOne = false;//booleans for stopping discovery
    private boolean deviceTwo = false;//booleans for stopping discovery
    private ArrayList<String> connectedDevices;

    // Below starts the connection handler:
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_CONNECT:
                    ConnectedThread connectedThread = new ConnectedThread((BluetoothSocket) msg.obj);
                    toastMaker("YOU ARE NOW CONNECTED");
                    bar.setVisibility(View.GONE);
                    String s = "Successfully connected";
                    connectedThread.write(s.getBytes());
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String st = new String(readBuf);
                    toastMaker(st);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        setupUI();

        //below are actions registered in the receiver
        filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(discoveryResult, filter);

        //when discovery starts, discovery could also be cancelled
        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setMessage("Scanning...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                theAdapter.cancelDiscovery();
            }
        });

    }

    public void setupUI() {

        connectedDevices = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.listView);//the list with the paired items
        listView.setOnItemClickListener(this); // registers callback when an item been clicked
        mylist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0);//layout
        listView.setAdapter(mylist);//pouring myList into the listView
        exit = (Button) findViewById(R.id.button1);// a button to terminate the application
        exit.setVisibility(View.VISIBLE);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppExit(); //method to terminate the application, avialable below!
            }
        });


        bar = (ProgressBar) findViewById(R.id.progressBar);// bar indicator of pairing and connection status
        bar.setVisibility(View.GONE);
        activate = (Button) findViewById(R.id.button);
        activate.setVisibility(View.VISIBLE);
        activate.setOnClickListener(new View.OnClickListener() { //turning on and off bluetooth and changing text of button
            @Override
            public void onClick(View v) {
                if (!theAdapter.isEnabled()) {
                    setBluetooth(true);
                    activate.setText("Turn Off");
                    bar.setVisibility(View.VISIBLE);
                } else {
                    setBluetooth(false);
                    activate.setText("Discover And Pair");
                    bar.setVisibility(View.GONE);
                }
            }
        });



        // ArrayList for connecting to a bluetooth device through the ListView. This ArrayList will
        // keep device items in the same position as the ListView. For example, position 1 is clicked
        // on the ListView, position 1 in the ArrayList mDeviceList holds this specific item shown
        // on the UI via the ListView.
        mDeviceList = new ArrayList<BluetoothDevice>();
    }
    //goes to next activity
    private void next(){
        startActivity(new Intent(this, UserActivity.class));
    }
    private void back() {
        startActivity(new Intent(this, BluetoothActivity.class));
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (theAdapter.isDiscovering()) { // checking to Cancel discovery if on at connecting
            cancelDisc();
        }

        BluetoothDevice selectedDevice = mDeviceList.get(position); //getting the position in mDeviceList
        ConnectThread connect = new ConnectThread(selectedDevice); //passing in the selectedDevice and connecting it
        connect.start();
        bar.setVisibility(View.VISIBLE);
    }

    // this method turn on and off bluetooth device
    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        return true;
    }

    // this method terminates the application and exits back to the androids main view
    public void AppExit()
    {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * this method, pairs, and adds items to the list, also starts and ends discovery.
     *
     */
    private void checkItems() {


        paired_devices = theAdapter.getBondedDevices();

        if (paired_devices.size() != 0) {
            for (BluetoothDevice device : paired_devices) {
                if (device.getName().equals("Group 2")) {

                    if (!mDeviceList.contains(device)) {
                        //String s = "(Paired)";
                        mylist.add("Click To Connect: "+device.getName() + " " + " " + "\n" + "Address: "+device.getAddress());
                        mDeviceList.add(device);
                        deviceOne = true;
                    }
                    listView.setAdapter(mylist);

                } else if (device.getName().equals("MindWave Mobile")) {
                    if (!mDeviceList.contains(device)) {
                        String s = "(Paired)";
                        mylist.add("Click To Connect: "+device.getName() + " " + " " + "\n" + "Address: "+device.getAddress());
                        mDeviceList.add(device);

                        deviceTwo = true;
                    }

                    listView.setAdapter(mylist);
                }
            }
            if (!deviceOne || !deviceTwo) {
                theAdapter.startDiscovery();
            } else {
                bar.setVisibility(View.GONE);
            }
        } else {
            theAdapter.startDiscovery();
        }
    }
    //this method simply prints toasts anywhere in the code as needed
    private void toastMaker(String input) {
        Toast.makeText(BluetoothActivity.this, input, Toast.LENGTH_LONG).show();
    }

    //here is the receiver and all the actions being registered and events happen as the result proceeds.
    private final BroadcastReceiver discoveryResult = new BroadcastReceiver() {

        @Override
        @TargetApi(Build.VERSION_CODES.KITKAT) // this Api works with the method "CreateBond"
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (theAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    checkItems();
                }
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                toastMaker("Discovery started...");
                mProgressDlg.show();
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                toastMaker("Discovery ended.");
                mProgressDlg.dismiss();
            }

            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                if(device.getName().equals("Group 2") || device.getName().equals("MindWave Mobile")){
                    connectedDevices.add(device.getName());
                }if(connectedDevices.contains("Group 2") && connectedDevices.contains("MindWave Mobile")){
                    next();
                }
            }

            if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                connectedDevices.remove(device.getName());
                if(connectedDevices.size() == 1) {
                    back();
                }
            }

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName() != null) {
                    if (device.getName().equals("Group 2") ) {
                        device.createBond();

                    } else if (device.getName().equals("MindWave Mobile")) {
                        device.createBond();
                    }
                }
            }

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    toastMaker(device.getName() + " has been paired successfully!");
                    checkItems();
                }
            }
        }

    };
    //this method cancels discovery and is called in the onClickListener of the ListView
    private void cancelDisc() {
        if (theAdapter.getState() == BluetoothAdapter.STATE_ON) {
            theAdapter.cancelDiscovery();
        }
    }

    /*
     * this class is the connecting class.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {}
            mmSocket = tmp;
        }

        public void run() {
            theAdapter.cancelDiscovery();

            try {
                mmSocket.connect();

            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) {}
                return;
            }

            // Do work to manage the connection (in a separate thread)
            mHandler.obtainMessage(SUCCESS_CONNECT, mmSocket).sendToTarget();
        }


        // Will cancel the connection, and close the socket
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {}
        }
    }

    //this class is for the in-coming and out-coming data
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {}

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer;  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    buffer = new byte[1024];
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the ui activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {}
        }

        // Call this from the main activity to shutdown the connection
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {}
        }
    }
}