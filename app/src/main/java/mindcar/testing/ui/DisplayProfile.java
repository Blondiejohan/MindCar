package mindcar.testing.ui;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayprofile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
            if(msg.what== TGDevice.MSG_ATTENTION){
                Connected.write(CommandUtils.toByteArray(car.getCommands()));
                MessageParser.parseMessage(msg, car, eeg);
                // att.setText(eeg.getAttention() + "");
                attentionBar.setProgress(eeg.getAttention());
            }

        }
    };

}
