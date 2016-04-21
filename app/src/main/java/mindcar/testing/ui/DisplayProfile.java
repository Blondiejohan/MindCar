package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;

import mindcar.testing.R;
import mindcar.testing.objects.ComparePatterns;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;


/**
 * Created by madiseniman on 07/04/16.
 */
public class DisplayProfile extends AppCompatActivity {

    SmartCar car;
    EEGObject eeg;
    TGDevice tgDevice;
    ProgressBar attentionBar;
    Button patterns;
    Button start;
    TextView attention;
    private BluetoothAdapter dpAdapter;// this class intitilizes bt hardware on a device


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayprofile);
        dpAdapter = BluetoothAdapter.getDefaultAdapter();
        tgDevice = new TGDevice(dpAdapter, handler);
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
            tgDevice.start();
        }
        patterns = (Button) findViewById(R.id.patterns);
        start = (ToggleButton) findViewById(R.id.toggleButton);
        attention = (TextView) findViewById(R.id.attention);
        attentionBar = (ProgressBar) findViewById(R.id.attentionBar);
        patterns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgDevice.stop();
                startActivity(new Intent(DisplayProfile.this, SavePatterns.class));

            }
        }); // end patterns

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attention.setText("test");

            }
        }); // end start
    }

    public String getUserName(String un){
        String username = null;
        //code for retrieving the username from the database
        return username;
    }

    public int getBatteryLevel(){
        int batterylvl = 0;
        //code for getting and displaying the SmartCar's battery level
        return batterylvl;
    }

    public int getSpeed(){
        int speed = 0;
        //code for getting and displaying a live reading of SmartCar's speed while driving.
        return speed;
    }


    /**
     * Handles messages from TGDevice
     */
    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what== TGDevice.MSG_EEG_POWER){
                ComparePatterns comp = new ComparePatterns((TGEegPower) msg.obj);
                if(comp.compare("left")){
                    attention.setText("Left");
                    Log.i("result","Matched Left");
                    //Connected.write("l");
                }if (comp.compare("right")){
                    attention.setText("Right");
                    Log.i("result", "Matched Right");
                    //Connected.write("r");
                }if (comp.compare("forward")){
                    attention.setText("Forward");
                    Log.i("result", "Matched Forward");
                    //Connected.write("f");
                }if (comp.compare("stop")){
                    attention.setText("Stop");
                    Log.i("result", "Matched Stop");
                    //Connected.write("s");
                }
                //Connected.write(CommandUtils.toByteArray(car.getCommands()));
                //MessageParser.parseMessage(msg, car, eeg);
                //attentionBar.setProgress(msg.arg1);
                //Toast.makeText(getApplicationContext(), "Attention = "+msg, Toast.LENGTH_SHORT).show();

            }
            }
    };

}
