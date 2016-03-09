package mindcar.testing.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.neurosky.thinkgear.TGDevice;

import java.util.Set;

import mindcar.testing.objects.Connected;
import mindcar.testing.R;
import mindcar.testing.objects.Connection;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;
import mindcar.testing.util.MessageParser;

public class MainActivity extends Activity {
    BluetoothAdapter bluetoothAdapter;
    Connection conn;

    TextView tv;
    TextView att;
    TextView med;
    TextView blink;
    Button go;
    TextView noise;
    TextView raw;
    TextView dev;
    TGDevice tgDevice;
    final boolean rawEnabled = true;
    
    EEGObject eeg = new EEGObject();
    SmartCar car = new SmartCar();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        att = (TextView) findViewById(R.id.att);
        med = (TextView) findViewById(R.id.med);
        blink = (TextView) findViewById(R.id.blink);
        tv = (TextView) findViewById(R.id.textView1);
        noise = (TextView) findViewById(R.id.noise);
        raw = (TextView) findViewById(R.id.raw);
        dev = (TextView) findViewById(R.id.dev);
        BluetoothDevice mDevice = null;
        go = (Button) findViewById(R.id.go);

        tv.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            // Alert user that Bluetooth is not available
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }else {
        	/* create the TGDevice */

            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices.size() > 0) {
                for (BluetoothDevice device : devices) {

                        mDevice=device;
                }
            }
            String tmpDev = "Dev: " + mDevice.getName() + "\n";
            dev.setText(tmpDev);
            tgDevice = new TGDevice(bluetoothAdapter, handler);
        }
        doStuff(tv);
        conn = new Connection(mDevice);
        conn.start();


    }

    @Override
    public void onDestroy() {
        tgDevice.close();
        super.onDestroy();
    }
    /**
     * Handles messages from TGDevice
     */
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Connected.write(CommandUtils.toByteArray(car.getCommands()));
            MessageParser.parseMessage(msg, car, eeg);
        }
    };

    public void doStuff(View view) {
        if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
            tgDevice.connect(rawEnabled);
    }
}
