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
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.FindPattern;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;

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

    int nrOfTimes = 20;
    int updateNr = 20;
    TextView direction;
    TextView text;
    Button test;
    DatabaseAccess databaseAccess;
    int start = 1;
    public boolean isConnected;
    Eeg eeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_patterns);
        direction = (TextView) findViewById(R.id.direction);
        text = (TextView) findViewById(R.id.emptyText);
        test = (Button) findViewById(R.id.test);
        spAdapter = BluetoothAdapter.getDefaultAdapter();
        isConnected = false;
        databaseAccess = DatabaseAccess.getInstance(this);
        tgDevice = new TGDevice(spAdapter, tgHandler);
        eeg = new Eeg();

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


    public void saveLeft(Eeg eeg) {
        if (leftBool) {
            left = new FindPattern(eeg);
            leftBool = false;
        }
        if (updateNr >= 0) {
            left.updateProfile(eeg);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("left", left.getPattern(nrOfTimes));
            Log.i("test","Left "+left.getPattern(nrOfTimes));
            databaseAccess.close();
            start = 2;
        }

    }

    public void saveRight(Eeg eeg) {
        if (rightBool) {
            right = new FindPattern(eeg);
            rightBool = false;
        }
        if (updateNr > 0) {
            right.updateProfile(eeg);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("right", right.getPattern(nrOfTimes));
            Log.i("test","Right "+right.getPattern(nrOfTimes));
            databaseAccess.close();
            start = 3;
        }

    }

    public void saveForward(Eeg eeg) {
        if (forwardBool) {
            forward = new FindPattern(eeg);
            forwardBool = false;
        }
        if (updateNr > 0) {
            forward.updateProfile(eeg);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("forward", forward.getPattern(nrOfTimes));
            Log.i("test", "Forward " + forward.getPattern(nrOfTimes));
            databaseAccess.close();
            start = 4;
        }

    }

    public void saveStop(Eeg eeg) {
        if (stopBool) {
            stop = new FindPattern(eeg);
            stopBool = false;
        }
        if (updateNr > 0) {
            stop.updateProfile(eeg);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addDirection("stop", stop.getPattern(nrOfTimes));
            Log.i("test", "Stop " + stop.getPattern(nrOfTimes));
            databaseAccess.close();
            start = 5;
            tgDevice.close();
            startActivity(new Intent(SavePatterns.this, UserActivity.class));
        }

    }

    /**
     * Handler that creates the pattern for left, it first creates a findpattern, then updates it several times
     * to get the low and high for each wave. It then sends the result to the database.
     */
    public final Handler tgHandler = new Handler() {
        @Override
            public void handleMessage (Message msg){
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
                            if (start == 1){
                                direction.setText("Think Left");
                                saveLeft(eeg);
                            }
                            if (start == 2){
                                direction.setText("Think Right");
                                saveRight(eeg);
                            }
                            if (start == 3){
                                direction.setText("Think Forward");
                                saveForward(eeg);
                            }
                            if (start == 4){
                                direction.setText("Think Stop");
                                saveStop(eeg);
                            }
                            eeg = new Eeg();
                            break;
                        } else {
                            MessageParser.parseRawData(msg, eeg);
                            break;
                        }
                }
            }
    };
}
