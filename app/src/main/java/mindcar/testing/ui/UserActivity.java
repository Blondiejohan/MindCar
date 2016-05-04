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
import android.widget.TextView;

import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Command;
import mindcar.testing.objects.ComparePatterns;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;


/**
 * Created by madiseniman and johan
 */

public class UserActivity extends AppCompatActivity {

    private SmartCar car;
    private TGDevice tgDevice;
    private Pattern<Eeg> pattern;
    private Command x;
    public Button save;
    public Button restart;
    public boolean isConnected;
    public TextView delta;
    public TextView theta;
    public TextView lowAlpha;
    public TextView highAlpha;
    public TextView lowBeta;
    public TextView highBeta;
    public TextView lowGamma;
    public TextView highGamma;
    Eeg eeg;
    DatabaseAccess databaseAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = false;
        eeg = new Eeg();
        databaseAccess = DatabaseAccess.getInstance(this);
        setContentView(R.layout.activity_user);
        save = (Button) findViewById(R.id.pattern);
        restart = (Button) findViewById(R.id.restart);
        delta = (TextView) findViewById(R.id.delta);
        theta = (TextView) findViewById(R.id.theta);
        lowAlpha = (TextView) findViewById(R.id.lowAlha);
        highAlpha = (TextView) findViewById(R.id.highAlpha);
        lowBeta = (TextView) findViewById(R.id.lowBeta);
        highBeta = (TextView) findViewById(R.id.highBeta);
        lowGamma = (TextView) findViewById(R.id.lowGamma);
        highGamma = (TextView) findViewById(R.id.highGamma);

        double[] testDoubles = {2,5,8,11,15,25,36,46};


        pattern = new Pattern<>();
        car = new SmartCar();
        x = car.getCommands();
        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgDevice.close();
                startActivity(new Intent(UserActivity.this, SavePatterns.class));

            }
        });


        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgDevice.close();
                if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                        && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
                    tgDevice.connect(true);
                    tgDevice.start();
                }
            }
        }); // end patterns






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
                            Log.i("wave", "Connecting");
                            break;
                    }
                    break;

                case TGDevice.MSG_RAW_DATA:
                    if (eeg.isFull()) {
                        ComparePatterns compPatt = new ComparePatterns(eeg);
                        String send = compPatt.compare(databaseAccess);
                        restart.setText(send);
                        delta.setText(eeg.delta + "");
                        theta.setText(eeg.theta+"");
                        lowAlpha.setText(eeg.lowAlpha+"");
                        highAlpha.setText(eeg.highAlpha+"");
                        lowBeta.setText(eeg.lowBeta+"");
                        highBeta.setText(eeg.highBeta+"");
                        lowGamma.setText(eeg.lowGamma+"");
                        highGamma.setText(eeg.highGamma+"");
                        eeg = new Eeg();
                        break;
                    }else{
                        MessageParser.parseRawData(msg, eeg);
                        break;
                    }
            }
        }
    };

}
