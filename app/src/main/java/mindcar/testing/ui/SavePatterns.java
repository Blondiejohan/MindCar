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


    TGDevice tgDevice;

    private BluetoothAdapter spAdapter;// this class intitilizes bt hardware on a device
    Pattern baseline;
    boolean baseBool = true;

    Pattern right;
    boolean rightBool = true;

    Pattern forward;
    boolean forwardBool = true;

    Pattern left;
    boolean leftBool = true;

    Pattern stop;
    boolean stopBool = true;

    int nrOfTimes = 100;
    int updateNr = 100;
    int times = 20;
    TextView direction;
    TextView text;
    Button test;
    DatabaseAccess databaseAccess;
    int start = 1;
    public boolean isConnected;
    Eeg eeg;
    Eeg tmp;
    private int timesSaved = 0;


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

        if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
        }
        eeg = new Eeg();

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

    public void saveBaseline(Eeg eeg) {
        if (baseBool) {

            baseline = new Pattern(eeg, 500);
            baseBool = false;
        }
        if (updateNr >= 0) {
            baseline.add(eeg);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("baseline", this.toString(baseline.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", this.toString(baseline.toArray()));

            databaseAccess.close();
            start = 2;
        }

    }

    public void saveLeft(Eeg eeg) {
        if (leftBool) {
            left = new Pattern(eeg,500);
            leftBool = false;
        }
        if (updateNr >= 0) {
            left.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("left", this.toString(left.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", this.toString(left.toArray()));

            databaseAccess.close();
            start = 3;
        }

    }

    public void saveRight(Eeg eeg) {
        if (rightBool) {
            right = new Pattern(eeg,500);
            rightBool = false;
        }
        if (updateNr > 0) {
            right.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("right", this.toString(right.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", this.toString(right.toArray()));

            databaseAccess.close();
            start = 4;
        }

    }

    public void saveForward(Eeg eeg) {
        if (forwardBool) {
            forward = new Pattern(eeg,500);
            forwardBool = false;
        }
        if (updateNr > 0) {
            forward.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("forward", this.toString(forward.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", this.toString(forward.toArray()));

            databaseAccess.close();
            start = 5;
        }

    }

    public void saveStop(Eeg eeg) {
        if (stopBool) {
            stop = new Pattern(eeg,500);
            stopBool = false;
        }
        if (updateNr > 0) {
            stop.add(eeg,baseline);
            text.setText(updateNr + "");
            updateNr = updateNr - 1;
        } else {
            updateNr = nrOfTimes;
            databaseAccess.open();
            databaseAccess.addPattern("stop", this.toString(stop.toArray()), RegistrationActivity.user_name);
            Log.i("Pattern", this.toString(stop.toArray()));

            databaseAccess.close();

            if(timesSaved < 5){
                start = 1;
                timesSaved++;
                startValueSetup();

            } else {
                tgDevice.stop();
                tgDevice.close();
                startActivity(new Intent(SavePatterns.this, StartActivity.class));
                this.finish();
            }
        }

    }

    /**
     * Handler that creates the pattern for left, it first creates a Pattern, then updates it several times
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
                        if (times == 0){
                            if(start == 1){
                                direction.setText("Establishing baseline \n please relax");
                                if(eeg.isFull()) {
                                    saveBaseline(eeg);
                                    tmp = eeg;
                                } else if(baseline != null && baseline.get(0) != null){
                                    eeg.populate(tmp);
                                    saveBaseline(eeg);
                                }
                            }
                            if (start == 2){
                                direction.setText("Think Left");
                                saveLeft(eeg);

                            }
                            if (start == 3){
                                direction.setText("Think Right");
                                saveRight(eeg);
                            }
                            if (start == 4){
                                direction.setText("Think Forward");
                                saveForward(eeg);
                            }
                            if (start == 5){
                                direction.setText("Think Stop");
                                saveStop(eeg);
                            }
                            eeg = new Eeg();
                            times = 20;
                            break;
                        } else {
                            MessageParser.parseRawData(msg, eeg);
                            times--;
                            break;
                        }
                }
            }
    };

    public String toString(double[] doubles){
        StringBuilder str = new StringBuilder();
        for(double d: doubles){
            str.append("s" + d + "e");
        }
        return str.toString();
    }

    public void startValueSetup(){
        updateNr = 100;
        nrOfTimes = 100;
        times = 20;
    }
}
