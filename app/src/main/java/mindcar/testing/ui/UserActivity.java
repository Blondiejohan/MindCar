package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Command;
import mindcar.testing.objects.Connected;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;
import mindcar.testing.util.MessageParser;


/**
 * Created by madiseniman on 07/04/16.
 */
public class UserActivity extends AppCompatActivity {

    private SmartCar car;
    private Eeg eeg;
    private TGDevice tgDevice;
    private Pattern<Eeg> pattern;
    private Command x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        pattern  = new Pattern<>();
        car = new SmartCar();
        x = car.getCommands();
        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        tgDevice.start();
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
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                eeg = new Eeg();
                if(car.getCommands()!= x) {
                    Connected.write(CommandUtils.toByteArray(car.getCommands()));
                }
                MessageParser.parseMessage(msg, eeg);
                pattern.add(eeg);
                x = car.getCommands();
            }


    };

}
