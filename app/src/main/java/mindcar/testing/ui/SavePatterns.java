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
import com.neurosky.thinkgear.TGEegPower;

import mindcar.testing.R;
import mindcar.testing.objects.FindPattern;
import mindcar.testing.util.DatabaseAccess;

/**
 * Created by johan
 */

/**
 * This class creates patterns for left,right,forward and stop.
 */
public class SavePatterns extends AppCompatActivity {


    TGDevice tgDevice;

    private BluetoothAdapter spAdapter;// this class intitilizes bt hardware on a device
    FindPattern right;
    boolean rightBool = true;

    FindPattern forward;
    boolean forwardBool = true;

    FindPattern left;
    boolean leftBool = true;

    FindPattern stop;
    boolean stopBool = true;

    int nrOfTimes = 5;
    int updateNr = 5;
    TextView direction;
    TextView text;
    Button test;
    DatabaseAccess databaseAccess;
    int start = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_patterns);
        direction = (TextView) findViewById(R.id.direction);
        text = (TextView) findViewById(R.id.text);
        test = (Button) findViewById(R.id.test);
        spAdapter = BluetoothAdapter.getDefaultAdapter();
        databaseAccess = DatabaseAccess.getInstance(this);
        tgDevice = new TGDevice(spAdapter, tgHandler);
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
            tgDevice.start();
        }
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgDevice.stop();
                if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                        && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
                    tgDevice.connect(true);
                    tgDevice.start();
                }
            }
        }); // end patterns

    }


    public void saveLeft(Message msg) {
        TGEegPower waves = (TGEegPower) msg.obj;
        if (leftBool) {
            left = new FindPattern(waves);
            leftBool = false;
        }
        if (updateNr >= 0) {
            left.updateProfile(waves);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("left", left.getPattern());
            Log.i("leftpattern", left.getPattern());
            databaseAccess.close();
            start = 2;
        }

    }

    public void saveRight(Message msg) {
        TGEegPower waves = (TGEegPower) msg.obj;
        if (leftBool) {
            left = new FindPattern(waves);
            leftBool = false;
        }
        if (updateNr > 0) {
            left.updateProfile(waves);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("right", left.getPattern());
            databaseAccess.close();
            start = 3;
        }

    }

    public void saveForward(Message msg) {
        TGEegPower waves = (TGEegPower) msg.obj;
        if (leftBool) {
            left = new FindPattern(waves);
            leftBool = false;
        }
        if (updateNr > 0) {
            left.updateProfile(waves);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("forward", left.getPattern());
            databaseAccess.close();
            start = 4;
        }

    }

    public void saveStop(Message msg) {
        TGEegPower waves = (TGEegPower) msg.obj;
        if (leftBool) {
            left = new FindPattern(waves);
            leftBool = false;
        }
        if (updateNr > 0) {
            left.updateProfile(waves);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("stop", left.getPattern());
            databaseAccess.close();
            start = 5;
            tgDevice.stop();
            startActivity(new Intent(SavePatterns.this, UserActivity.class));
        }

    }

    /**
     * Handler that creates the pattern for left, it first creates a findpattern, then updates it several times
     * to get the low and high for each wave. It then sends the result to the database.
     */
    public final Handler tgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case TGDevice.MSG_EEG_POWER:
                    if (start == 1) {
                        direction.setText("Think Left");
                        saveLeft(msg);
                    } else if (start == 2) {
                        direction.setText("Think Right");
                        saveRight(msg);
                    } else if (start == 3) {
                        direction.setText("Think Forward");
                        saveForward(msg);
                    } else if (start == 4) {
                        direction.setText("Think Stop");
                        saveStop(msg);
                    }
                    break;
            }
        }
    };
}
