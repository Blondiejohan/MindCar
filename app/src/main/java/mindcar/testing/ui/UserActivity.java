package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Command;
import mindcar.testing.objects.ComparePatterns;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;
import mindcar.testing.util.NeuralNetworkHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

import java.util.LinkedList;


/**
 * Created by madiseniman on 07/04/16.
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartCar car;
    private TGDevice tgDevice;
    private Pattern pattern;
    private Command x;
    boolean isConnected = false;
    Button restart;
    NeuralNetwork neuralNetwork;
    Eeg eeg;
    DatabaseAccess databaseAccess;

    String name = StartActivity.un;
    String pw = StartActivity.pw;


    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    View v;
    TextView username;
    Button logout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = false;
        eeg = new Eeg();
        databaseAccess = DatabaseAccess.getInstance(this);
        setContentView(R.layout.activity_user);

        LinkedList<double[]> patternList = new LinkedList<>();
    //    patternList.add(databaseAccess.getDirection("left"));
    //    patternList.add(databaseAccess.getDirection("right"));
    //    patternList.add(databaseAccess.getDirection("forward"));
    //    patternList.add(databaseAccess.getDirection("stop"));

        patternList.add(new double[160]);
        patternList.add(new double[160]);
        patternList.add(new double[160]);
        patternList.add(new double[160]);

        DataSet dataSet = NeuralNetworkHelper.createDataSet(patternList, 160, 1);
        neuralNetwork = NeuralNetworkHelper.createNetwork(dataSet,160,1);



        pattern = new Pattern();
        car = new SmartCar();
        x = car.getCommands();
        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        tgDevice.start();

        logout = (Button) findViewById(R.id.logout);
        displayName(v);
        //username.setVisibility(View.VISIBLE);
        System.out.println("The user name passed to UserActivity from StartActivity is: " + name);
        logout.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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






    public void displayName(View view) {
        // SharedPreferences sharedpreferences = getSharedPreferences("displayUserName", Context.MODE_PRIVATE);
        // String name = sharedpreferences.getString("username", "");
        username = (TextView) findViewById(R.id.username);
        username.setText(name);


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.logout:
                startActivity(new Intent(this, StartActivity.class));
        }
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
            Log.i("Time start ", System.currentTimeMillis() + "");
            int times = 20;

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

                        MessageParser.parseRawData(msg, eeg);

                        if (times == 0){
                            pattern.add(eeg);

                            ComparePatterns compPatt = new ComparePatterns(pattern.toArray(), neuralNetwork);
                            String send = compPatt.compare(databaseAccess);
                            Log.i("Send message " , send);
                            eeg = new Eeg();
                            times = 20;
                            Log.i("Time stop ", System.currentTimeMillis() + "");
                        }else{
                            times--;
                        }

                        break;

            }
        }
    };
}