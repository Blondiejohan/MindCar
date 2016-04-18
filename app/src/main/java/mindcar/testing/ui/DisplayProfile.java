package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Connected;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;
import mindcar.testing.util.MessageParser;


/**
 * Created by madiseniman on 07/04/16.
 */
public class DisplayProfile extends AppCompatActivity {

    SmartCar car;
    EEGObject eeg;
    TGDevice tgDevice;
    ProgressBar attentionBar;
    Button patterns;
    private BluetoothAdapter dpAdapter;// this class intitilizes bt hardware on a device

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayprofile);
        dpAdapter = BluetoothAdapter.getDefaultAdapter();
        tgDevice = new TGDevice(dpAdapter,handler);
        patterns = (Button) findViewById(R.id.patterns);
        attentionBar = (ProgressBar) findViewById(R.id.attentionBar);
        patterns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayProfile.this, SavePatterns.class));
            }
        }); // end patterns
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
            if(msg.what== TGDevice.MSG_ATTENTION){
                Connected.write(CommandUtils.toByteArray(car.getCommands()));
                MessageParser.parseMessage(msg, car, eeg);
                attentionBar.setProgress(msg.arg1);
                //Toast.makeText(getApplicationContext(), "Attention = "+msg, Toast.LENGTH_SHORT).show();
            }
            }
    };

}
