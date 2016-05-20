package mindcar.testing.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;

import java.util.ArrayList;
import java.util.Set;

import mindcar.testing.R;
import mindcar.testing.objects.Connection;


/**
 * Created by Sarah And Johan, refactored and commented by Sarah on 2016-05-01.
 * This class starts the bluetooth adapter, then pairs the adapter to Nemesis system.
 */

public class BluetoothActivity extends Activity implements AdapterView.OnItemClickListener {

    //public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    protected static final int SUCCESS_CONNECT = 0;//Handler for situation of connection status
    protected static final int MESSAGE_READ = 1;//Handler for situation of connection status
    private Button exit; // terminates the application
    private Button activate;// turns Bluetooth on and initiates methods
    private ListView listView;//shows paired items
    private ProgressDialog mProgressDlg;//for discovery progress
    private ProgressBar bar;// waiting indicator for pairing and connecting
    public BluetoothAdapter theAdapter = BluetoothAdapter.getDefaultAdapter(); //the bluetooth adapter
    private Set<BluetoothDevice> paired_devices; //a set of bonded devices
    private ArrayAdapter<String> mylist; //viewed in the list view, the paired devices
    private ArrayList<BluetoothDevice> mDeviceList;// discovery list for adding requiered devices
    private IntentFilter filter; // an intent
    private boolean deviceOne = false;//booleans for stopping discovery
    private boolean deviceTwo = false;//booleans for stopping discovery
    private ArrayList<String> connectedDevices;
    public static TGDevice tgDevice;
    boolean isConnected = false;
    //private ArrayList<String> delete;

    // Below starts the connection handler:
   public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:
                    switch (msg.arg1) {
                        case TGDevice.STATE_CONNECTED:
                            isConnected = true;
                            tgDevice.start();
                            Log.i("wave", "Connecting");
                            break;
                    }
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
        mProgressDlg.setMessage("Scanning For Devices...");
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

        //delete = new ArrayList<>();
        connectedDevices = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);//the list with the paired items

        listView.setOnItemClickListener(this); // registers callback when an item been clicked

        mylist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0);//layout

        listView.setAdapter(mylist);//pouring myList into the listView

        //mylist.notifyDataSetChanged();

        bar = (ProgressBar) findViewById(R.id.progressBar2);// bar indicator of pairing and connection status

        bar.setVisibility(View.GONE);

        activate = (Button) findViewById(R.id.button);

        activate.setVisibility(View.VISIBLE);

        activate.setOnClickListener(new View.OnClickListener() { //turning on and off bluetooth and changing text of button
            @Override
            public void onClick(View v) {

                if (!theAdapter.isEnabled()) {
                    setBluetooth(true);
                    bar.setVisibility(View.VISIBLE);
                } else {
                    bar.setVisibility(View.GONE);
                    checkItems();
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
    private void next() {
        startActivity(new Intent(this, StartActivity.class));
        this.finish();
    }

    private void back() {
        startActivity(new Intent(this, BluetoothActivity.class));
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        if (theAdapter.isDiscovering()) { // checking to Cancel discovery if on at connecting
            cancelDisc();
        }

        //String item = delete.get(position);

        BluetoothDevice selectedDevice = mDeviceList.get(position); //getting the position in mDeviceList

        if (selectedDevice.getName().equals("Group 2")){

            Connection connect = new Connection(selectedDevice); //passing in the selectedDevice and connecting it
            connect.start();
            bar.setVisibility(View.VISIBLE);
            //delete.remove(position);
            //mylist.remove(selectedDevice.getName());
            //mylist.notifyDataSetChanged();

            //mylist.remove(delete.get(position));
            //view.setEnabled(false);
            //view.setOnClickListener(null);
            //Log.i("whats in", mDeviceList.size() + "");


        } else if (selectedDevice.getName().equals("MindWave Mobile")){
            tgDevice = new TGDevice(theAdapter,mHandler);
            if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                    && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
                tgDevice.connect(true);
                bar.setVisibility(View.VISIBLE);
                //view.setEnabled(false);
                //view.setOnClickListener(null);
            }
        }

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

    /**
     * this method, pairs, and adds items to the list, also starts and ends discovery.
     */
    private void checkItems() {


        paired_devices = theAdapter.getBondedDevices();

        if (paired_devices.size() != 0) {
            for (BluetoothDevice device : paired_devices) {
                if (device.getName().equals("Group 2")) {

                    if (!mDeviceList.contains(device)) {
                        //String s = "(Paired)";
                        mylist.add("Click To Connect: " + device.getName() + " " + " " + "\n" + "Address: " + device.getAddress());
                        mDeviceList.add(device);
                        deviceOne = true;
                    }
                    listView.setAdapter(mylist);

                } else if (device.getName().equals("MindWave Mobile")) {
                    if (!mDeviceList.contains(device)) {
                       // String s = "(Paired)";
                        mylist.add("Click To Connect: " + device.getName() + " " + " " + "\n" + "Address: " + device.getAddress());
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
            if(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)){
                if(theAdapter.getState()== BluetoothAdapter.STATE_OFF){
                    toastMaker("BlueTooth Is Now Off");
                }
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //toastMaker("Discovery started...");
                mProgressDlg.show();
                bar.setVisibility(View.GONE);
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                toastMaker("Discovery ended.");
                mProgressDlg.dismiss();
            }

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                if ((!device.getName().equals(null) && (device.getName().equals("Group 2") || device.getName().equals("MindWave Mobile")))) {
                    connectedDevices.add(device.getName());
                    //mDeviceList.remove(device);
                    toastMaker("Connected to: " + device.getName());
                    bar.setVisibility(View.GONE);

                }
                if (connectedDevices.contains("Group 2") && connectedDevices.contains("MindWave Mobile")) {
                    tgDevice.stop();
                    tgDevice.close();
                   next();
                }
            }

            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                connectedDevices.remove(device.getName());
                toastMaker("Disconnected From: "+device.getName());
                if (device.getName().equals("Group 2")) {
                    //back();

                }
            }

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName() != null) {
                    if (device.getName().equals("Group 2")) {
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

}


