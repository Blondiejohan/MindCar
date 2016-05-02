package mindcar.testing.ui;
import android.annotation.TargetApi;
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
import android.widget.Toast;
import mindcar.testing.R;
import mindcar.testing.objects.Connection;


/**
 * Created by Johan And Sarah.
 */

public class BluetoothActivity extends Activity {

    private static final int DISCOVERY_REQUEST = 1;
    public static Connection conn;
    public Button connect;
    public ProgressBar loading;
    public TextView text;
    // we are going to call this when we the activity is created
    private BluetoothAdapter theAdapter;// this class intitilizes bt hardware on a device


    //  gets the defaultadapter
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        theAdapter = BluetoothAdapter.getDefaultAdapter();
        setupUI();
        text.setText("Turn the car and the headset on and press connect");
        connect.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);

    }

    // Sets up the button, text and loading icon. Also sets the onclicklisteners.
    public void setupUI() {
        connect = (Button) findViewById(R.id.connect);
        connect.setVisibility(View.GONE);
        loading = (ProgressBar) findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.text);

        // Click this to start looking for new devices, starts an activifyforresult that is discovery.
        // RegistrationActivity a reciever thats listens for new devices connecting
        // updates the text and switches the connect button for the loading icon.
        // If the correct devices isn't found withing 5 seconds it calls verify.
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theAdapter.getBondedDevices().toString().contains("20:15:10:20:03:47") && theAdapter.getBondedDevices().toString().contains("20:68:9D:91:D7:EF")) {
                    startActivity(new Intent(BluetoothActivity.this, UserActivity.class));

                } else {
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), DISCOVERY_REQUEST);
                    registerReceiver(bondedResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
                    text.setText("searching");
                    connect.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            connect.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            if (theAdapter.getBondedDevices().toString().contains("20:15:10:20:03:47")) {
                                text.setText("Try restarting the headset");

                            } else if (theAdapter.getBondedDevices().toString().contains("20:68:9D:91:D7:EF")) {
                                text.setText("Try restarting the car");

                            } else {
                                text.setText("Try restarting the car and headset");
                            }
                        }
                    }, 10000);
                }
            }
        }); // end connect
    }

    // This is the method that gets called when the startactivityforresult find a result.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == DISCOVERY_REQUEST) {
            if (!theAdapter.isDiscovering()) {
                findDevices();
            }
        }
    }

    // Sets a reciever that reacts to all the devices found.
    public void findDevices() { //check for devices

        if (theAdapter.startDiscovery()) {
            registerReceiver(bondedResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }



    // create a broadCast Receiver that gets called when new devices are found.
    // If both car and headset appear in bonded list it goes to main screen.
    // If a new device is found it checks if it is a new device or a paired device.
    // If a paired device is found it checks if it is the car and starts a connection with it.
    // if a new device is found it checks if it is the car or headset and if it is it starts a connection.
    protected BroadcastReceiver bondedResult = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bondDevice;
            bondDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.i("checkthing", bondDevice.getBondState()+ bondDevice.getName());


            if (theAdapter.getBondedDevices().toString().contains("20:15:10:20:03:47") && theAdapter.getBondedDevices().toString().contains("20:68:9D:91:D7:EF")) {
                startActivity(new Intent(BluetoothActivity.this, UserActivity.class));
            } else {
                String car = "Group 2";
                String headset = "MindWave Mobile";
                if (theAdapter.getBondedDevices().contains(bondDevice)) {
                    if (bondDevice.getName().equals(car)) {
                        Toast.makeText(getApplicationContext(), "Paired with car", Toast.LENGTH_LONG).show();
                        Connection conn = new Connection(bondDevice);
                        conn.start();
                    }
                    if (bondDevice.getName().equals(headset)) {
                        Toast.makeText(getApplicationContext(), "Paired with car", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (bondDevice.getName().equals(car) || bondDevice.getName().equals(headset)) {
                        Toast.makeText(getApplicationContext(), bondDevice.getName() + " found, trying to pair", Toast.LENGTH_LONG).show();
                        bondDevice.createBond();
                        registerReceiver(bondedResult, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));

                    }
                }

            }
        }

    };
}