package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.neurosky.thinkgear.TGDevice;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingSet;

import java.util.LinkedList;
import java.util.Set;

import mindcar.testing.R;
import mindcar.testing.objects.BackupControl;
import mindcar.testing.objects.Command;
import mindcar.testing.objects.ComparePatterns;
import mindcar.testing.objects.Connected;
import mindcar.testing.objects.Connection;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.EegBlink;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;
import mindcar.testing.util.NeuralNetworkHelper;


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

    //nikos
    Button userSettings;

    Button start;
    NeuralNetwork neuralNetwork;
    Eeg eeg;
    BackupControl backupControl;
    DatabaseAccess databaseAccess;
    EegBlink eegBlink;
    String name = StartActivity.un;
    String pw = StartActivity.pw;

    int times = 1000;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    View v;
    ImageView iv;
    TextView username;
    Button logout;
    ImageView userPic;
    Bitmap finalPic;
    ToggleButton toggle;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    Connection connection;
    //Connected connected;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        //nikos
        userSettings = (Button) findViewById(R.id.userSettings);
        logout = (Button) findViewById(R.id.logout);
        restart = (Button) findViewById(R.id.toggleButton);

        userSettings.setOnClickListener(this);

        isConnected = false;
        eeg = new Eeg();
        backupControl = new BackupControl();
        eegBlink = new EegBlink();
        databaseAccess = DatabaseAccess.getInstance(this);
        iv = (ImageView) findViewById(R.id.profile_image_view);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bl : devices) {
                Log.i("stuff", bl.getName().toString());
                if (bl.getName().equals("Group 2")) {
                    Log.i("stuff2", bl.getName().toString());
                    bluetoothDevice = bl;
                    connection = new Connection(bluetoothDevice);

                    //Connected.write("l");

                }
            }
        }


        neuralNetwork = databaseAccess.getNetwork(StartActivity.un);



        pattern = new Pattern();
        car = new SmartCar();
        x = car.getCommands();


        toggle = (ToggleButton) findViewById(R.id.toggleButton);

        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        tgDevice.connect(true);


        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (toggle.isChecked()) {
                    tgDevice.start();
                    connection.start();
                    Connected.write("f");
                } else {
                    tgDevice.stop();
                }

            }
        });
        displayName(v);


        //if      (databaseAccess.getPhoto(name) != null){

        displayPhoto(iv);//}

        username.setVisibility(View.VISIBLE);
        System.out.println("The user name passed to UserActivity from StartActivity is: " + name);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);


//        restart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tgDevice.close();
//                if (tgDevice.getState() != TGDevice.STATE_CONNECTING
//                        && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
//                    tgDevice.connect(true);
//                    tgDevice.start();
//                }
//            }
//        }); // end patterns
    }


    public void displayName(View view) {
        // SharedPreferences sharedpreferences = getSharedPreferences("displayUserName", Context.MODE_PRIVATE);
        // String name = sharedpreferences.getString("username", "");
        username = (TextView) findViewById(R.id.username);
        username.setText(name);
    }

    public void displayPhoto(ImageView p) {
        userPic = iv;
        byte[] image;
        System.out.println("The username that gets sent to getPhoto from displayPhoto is " + name);
        databaseAccess.open();
        image = databaseAccess.getPhoto(name);
        if (image != null) {
            //return;
            //byte[] pic = image.getBlob(1);
            finalPic = getImage(image);
            Drawable d = new BitmapDrawable(finalPic);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                userPic.setBackground(d);
            }
        }
        databaseAccess.close();
    }

    // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                tgDevice.stop();
                tgDevice.close();
                startActivity(new Intent(this, StartActivity.class));
                break;
            case R.id.userSettings:
                startActivity(new Intent(this, UserSettings.class));
                break;
        }
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
                            //tgDevice.start();
                            Log.i("wave", "Connecting");
                            break;
                    }
                    break;


                case TGDevice.MSG_RAW_DATA:

                    MessageParser.parseRawData(msg, eeg);

                    if (times == 0) {
                        Log.i("Time start ", System.currentTimeMillis() + "");
                        pattern.add(eeg);
                        ComparePatterns compPatt = new ComparePatterns(pattern.toArray(), neuralNetwork);
                        String send = compPatt.compare(databaseAccess);
                        Log.i("Send message ", send);
                        if(send == "w"){
                            Connected.write("f");
                        }
                        Connected.write(send);

                        eeg = new Eeg();
                        times = 1000;
                        Log.i("Time stop ", System.currentTimeMillis() + "");
                    } else {
                        times--;
                    }
                    break;
//                case TGDevice.MSG_BLINK:
//                    //Log.i("test", msg.arg1 + "");
//                    //SmartCar smartCar = new SmartCar();
//                    eegBlink.setBlink(msg.arg1);
//
//                    while (eegBlink.getAttention() > 40) {
//                        x = Command.f;
//                       // Log.i("test", "Forward");
//                        if (eegBlink.leftBlink()) {
//                          //  Log.i("test", "Left");
//                            x = Command.l;
//                            car.setCommand(x);
//                        }
//
//
//                        if (eegBlink.rightBlink()) {
//                           // Log.i("test", "Right");
//                            x = Command.r;
//                            car.setCommand(x);
//                        }
//
//                    }
//                    break;
//
//                case TGDevice.MSG_ATTENTION:
//                    eegBlink.setAttention(msg.arg1);
//                    if (eegBlink.getAttention() > 40) {
//                        x = Command.f;
//                        car.setCommand(x);
//                    }
//                    break;
            }
        }
    };

}