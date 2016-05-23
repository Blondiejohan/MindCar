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
import mindcar.testing.objects.Pattern;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;

/**
 * Created by johan
 */

/**
 * This class creates patterns for left,right,forward and stop.
 */
public class SavePatterns extends AppCompatActivity {

    //private BluetoothAdapter spAdapter;// this class intitilizes bt hardware on a device
    public static Pattern baseline;
    public static boolean baseBool = true;

    public static Pattern right;
    public static boolean rightBool = true;

    public static Pattern forward;
    public static boolean forwardBool = true;

    public static Pattern left;
    public static boolean leftBool = true;

    public static Pattern stop;
    public static boolean stopBool = true;

    public static int nrOfTimes = 100;
    public static int updateNr = 100;
    int times = 20;
    public static TextView direction;
    public static TextView text;
    public static DatabaseAccess databaseAccess;
    public static int start = 0;
    public boolean isConnected;
    public static Eeg eeg;
    public static Eeg tmp;
    private static Button moveOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_patterns);
        direction = (TextView) findViewById(R.id.direction);
        text = (TextView) findViewById(R.id.emptyText);
        moveOn = (Button) findViewById(R.id.moveOn);
        //spAdapter = BluetoothAdapter.getDefaultAdapter();
        isConnected = false;
        databaseAccess = DatabaseAccess.getInstance(this);
        //tgDevice = new TGDevice(spAdapter, tgHandler);

        //if (tgDevice.getState() != TGDevice.STATE_CONNECTING
        //        && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
        //    tgDevice.connect(true);
        //}
        eeg = new Eeg();

        moveOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWindow();
            }
        }); // end patterns

        start = 1;
        int times = 20;
        BluetoothActivity.startLearning = true;
    }

    public static void saveBaseline(Eeg eeg) {
        if (baseBool) {

            baseline = new Pattern(eeg);
            baseBool = false;
        }
        if (updateNr >= 0) {
            baseline.add(eeg);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("baseline", toString(baseline.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", toString(baseline.toArray()));

            databaseAccess.close();
            start = 2;
        }

    }

    public static void saveLeft(Eeg eeg) {
        if (leftBool) {
            left = new Pattern(eeg);
            leftBool = false;
        }
        if (updateNr >= 0) {
            left.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("left", toString(left.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", toString(left.toArray()));

            databaseAccess.close();
            start = 3;
        }

    }

    public static void saveRight(Eeg eeg) {
        if (rightBool) {
            right = new Pattern(eeg);
            rightBool = false;
        }
        if (updateNr > 0) {
            right.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("right", toString(right.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", toString(right.toArray()));

            databaseAccess.close();
            start = 4;
        }

    }

    public static void saveForward(Eeg eeg) {
        if (forwardBool) {
            forward = new Pattern(eeg);
            forwardBool = false;
        }
        if (updateNr > 0) {
            forward.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("forward", toString(forward.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", toString(forward.toArray()));

            databaseAccess.close();
            start = 5;
        }

    }

    public static void saveStop(Eeg eeg) {
        if (stopBool) {
            stop = new Pattern(eeg);
            stopBool = false;
        }
        if (updateNr > 0) {
            stop.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("stop", toString(stop.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", toString(stop.toArray()));

            databaseAccess.close();
            start = 6;
            moveOn.setVisibility(View.VISIBLE);
        }
    }

    public void nextWindow () {
        BluetoothActivity.startLearning=false;
        startActivity(new Intent(this, StartActivity.class));
        this.finish();

    }

    /**
     * Handler that creates the pattern for left, it first creates a Pattern, then updates it several times
     * to get the low and high for each wave. It then sends the result to the database.
     */
//    public final Handler tgHandler = new Handler() {
//        @Override
//            public void handleMessage (Message msg){
//
//            switch (msg.what) {
//                    case TGDevice.MSG_STATE_CHANGE:
//                        switch (msg.arg1) {
//                            case TGDevice.STATE_CONNECTED:
//                                isConnected = true;
//                                tgDevice.start();
//                                Log.i("wave", "Connecting");
//                                break;
//                        }
//                        break;
//
//                    case TGDevice.MSG_RAW_DATA:
//
//                        break;
//                }
//            }
//    };

    public static String toString(double[] doubles){
        StringBuilder str = new StringBuilder();
        for(double d: doubles){
            str.append("s" + d + "e");
        }
        return str.toString();
    }
}
