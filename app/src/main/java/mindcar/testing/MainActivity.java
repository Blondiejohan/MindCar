package mindcar.testing;

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
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;

import java.util.Set;

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
            switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:

                    switch (msg.arg1) {
                        case TGDevice.STATE_IDLE:
                            break;
                        case TGDevice.STATE_CONNECTING:
                            tv.append("Connecting...\n");
                            break;
                        case TGDevice.STATE_CONNECTED:
                            tv.append("Connected.\n");
                            tgDevice.start();
                            break;
                        case TGDevice.STATE_NOT_FOUND:
                            tv.append("Can't find\n");
                            break;
                        case TGDevice.STATE_NOT_PAIRED:
                            tv.append("not paired\n");
                            break;
                        case TGDevice.STATE_DISCONNECTED:
                            tv.append("Disconnected mang\n");
                    }

                    break;
                case TGDevice.MSG_POOR_SIGNAL:
                    //signal = msg.arg1;
                    String tmpNoise = "Noise: " + msg.arg1 + "\n";
                    noise.setText(tmpNoise);
                    break;
                case TGDevice.MSG_RAW_DATA:
                    //raw1 = msg.arg1;
                    String tmpRaw = "Raw Data: " + msg.arg1 + "\n";
                    raw.setText(tmpRaw);
                    break;
                case TGDevice.MSG_HEART_RATE:
                    tv.append("Heart rate: " + msg.arg1 + "\n");
                    break;
                case TGDevice.MSG_ATTENTION:
                    //att = msg.arg1;
                    String tmpAtt = "Attention: " + msg.arg1 + "\n";
                    att.setText(tmpAtt);
                    if(msg.arg1>50 && msg.arg1!=0) {
                        String f = "f";
                        Connected.write(f.getBytes());
                    }else{
                        String s = "s";
                        Connected.write(s.getBytes());
                    }
                    //Log.v("HelloA", "Attention: " + att + "\n");
                    break;
                case TGDevice.MSG_MEDITATION:
                    String tmpMed = "Meditation: " + msg.arg1 + "\n";
                    med.setText(tmpMed);
                    break;
                case TGDevice.MSG_BLINK:
                    String tmpBlink = "Blink: " + msg.arg1 + "\n";
                    blink.setText(tmpBlink);
                    break;
                case TGDevice.MSG_RAW_COUNT:
                    //tv.append("Raw Count: " + msg.arg1 + "\n");
                    break;
                case TGDevice.MSG_LOW_BATTERY:
                    Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
                    break;
                case TGDevice.MSG_RAW_MULTI:
                   // TGRawMulti rawM = (TGRawMulti)msg.obj;
                    //String tmpRaw = "Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2;
                    //raw.setText(tmpRaw);
                    break;
                default:
                    break;
            }
        }
    };

    public void doStuff(View view) {
        if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
            tgDevice.connect(rawEnabled);
    }
}