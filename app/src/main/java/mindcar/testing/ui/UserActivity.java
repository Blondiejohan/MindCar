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

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;

import mindcar.testing.R;
import mindcar.testing.objects.Command;
import mindcar.testing.objects.ComparePatterns;
import mindcar.testing.objects.EEGObject;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.DatabaseAccess;


/**
 * Created by madiseniman on 07/04/16.
 */

public class UserActivity extends AppCompatActivity {

    private SmartCar car;
    private EEGObject eeg;
    private TGDevice tgDevice;
    private Pattern<EEGObject> pattern;
    private Command x;
    public Button save;
    public boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = false;
        setContentView(R.layout.activity_user);
        save = (Button) findViewById(R.id.pattern);
        pattern = new Pattern<>();
        car = new SmartCar();
        x = car.getCommands();
        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(),handler);
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, SavePatterns.class));
                tgDevice.stop();
            }
        });
    }


    public String getUserName(String un) {
        String username = null;
        //code for retrieving the username from the database
        return username;
    }

    public int getBatteryLevel() {
        int batterylvl = 0;
        //code for getting and displaying the SmartCar's battery level
        return batterylvl;
    }

    public int getSpeed() {
        int speed = 0;
        //code for getting and displaying a live reading of SmartCar's speed while driving.
        return speed;
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
                        case TGDevice.STATE_CONNECTED:
                            isConnected = true;
                            tgDevice.start();
                            Log.i("handler1","Connecting");
                            break;
                    }
                    break;
                case TGDevice.MSG_RAW_DATA:
                    //save.setText(((msg.arg1*(1.8/4096))/2000)+"");
                    break;
                case TGDevice.MSG_EEG_POWER:
                    DatabaseAccess databaseAccess = new DatabaseAccess(UserActivity.this);
                    ComparePatterns compare = new ComparePatterns((TGEegPower) msg.obj);
                    if (compare.compare("left", databaseAccess)) {
                        //Connected.write("l");
                        save.setText("Left");
                    }
                    if (compare.compare("right", databaseAccess)) {
                        //Connected.write("r");
                        save.setText("Right");
                    }
                    if (compare.compare("forward", databaseAccess)) {
                        //Connected.write("f");
                        save.setText("Forward");
                    }
                    if (compare.compare("stop", databaseAccess)) {
                        //Connected.write("s");
                        save.setText("Stop");
                    }

            }

        }
    };

}
