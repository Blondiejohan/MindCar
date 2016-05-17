package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.TextView;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.ToggleButton;

import java.io.FileInputStream;


/**
 * Created by madiseniman on 07/04/16.
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartCar car;
    public static TGDevice tgDevice;
    private Pattern pattern;
    private Command x;
    boolean isConnected = false;
    Button restart, start;
    NeuralNetwork neuralNetwork;
    Eeg eeg;
    BackupControl backupControl;
    DatabaseAccess databaseAccess;

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = false;
        eeg = new Eeg();
        backupControl = new BackupControl();
        databaseAccess = DatabaseAccess.getInstance(this);
        setContentView(R.layout.activity_user);
        iv = (ImageView) findViewById(R.id.profile_image_view);
        

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        if(devices.size() > 0) {
            for (BluetoothDevice bl : devices) {
                Log.i("stuff", bl.getName().toString());
                if (bl.getName().equals("Group 2")) {
                    Log.i("stuff2",bl.getName().toString());
                    bluetoothDevice = bl;
                    connection = new Connection(bluetoothDevice);
                    connection.start();
                }
            }
        }



        LinkedList<double[]> patternList = new LinkedList<>();

        databaseAccess.open();
        patternList.add(databaseAccess.getDirection("left"));
        patternList.add(databaseAccess.getDirection("right"));
        patternList.add(databaseAccess.getDirection("forward"));
        patternList.add(databaseAccess.getDirection("stop"));
        databaseAccess.close();

        TrainingSet dataSet = NeuralNetworkHelper.createTrainingSet(patternList, patternList.get(0).length, 4);
        neuralNetwork = NeuralNetworkHelper.createNetwork(dataSet, patternList.get(0).length, 4);


        pattern = new Pattern();
        car = new SmartCar();
        x = car.getCommands();


        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                && tgDevice.getState() != TGDevice.STATE_CONNECTED) {
            tgDevice.connect(true);
            tgDevice.start();
        }
        tgDevice.start();*/
        toggle = (ToggleButton) findViewById(R.id.toggleButton);

        toggle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if (toggle.isChecked()){
                    tgdevice.start();

                }else{
                    tgdevice.stop();

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

        start = (Button) findViewById(R.id.toggleButton);
        start.setOnClickListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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


    //public void printUserName(){
    //String x = StartActivity.un;
    //getUserName();
    //username.append(name);
    //}

    //public void getUserName() {
    //code for retrieving the username from the database

    //String name = null;
    //String query = "SELECT userName FROM USERS WHERE username = '" +
    //un + "' AND password = '" + pw + "'";
    //if (name != null && pw != null) {
    // DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
    //databaseAccess.open();
    //Cursor resultSet = database.rawQuery(query, null);
    //resultSet.moveToFirst();
    //name = resultSet.getString(0);


    //SharedPreferences sharedpreferences = getSharedPreferences("username", Context.MODE_PRIVATE);

    //Editor editor = sharedpreferences.edit();
    // editor.putString("username", name);
    //editor.commit();

    //username.append(name);
    //databaseAccess.close();}
    //return username;}
    //else
    //System.out.println("Name not printing");
    //return null;
    // }


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
            case R.id.toggleButton:

                //testComparePatterns();
                break;
        }
    }

   /* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "User Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://mindcar.testing.ui/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "User Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://mindcar.testing.ui/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/


   /* public int getBatteryLevel() {
        int batterylvl = 0;
        //code for getting and displaying the SmartCar's battery level
        return batterylvl;
    }

    public int getSpeed() {
        int speed = 0;
        //code for getting and displaying a live reading of SmartCar's speed while driving.
        return speed;
    }*/


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

                case TGDevice.MSG_ATTENTION:        // Set car to forward or stop depending on attention level
                    backupControl.setAttention(msg.arg1);
                    if (backupControl.getAttention() >= 50){
                        car.setCommand(Command.f);
                    } else {
                        car.setCommand(Command.s);
                    }
                    break;

                case TGDevice.MSG_BLINK:            // Set car to left or right depending on blink state
//                    -=THIS USES BLINK STATE=-
                    backupControl.setBlink(msg.arg1);
                    if(backupControl.getBlinkState() == BackupControl.BLINK_HOLD){
                        car.setCommand(Command.l);
                    } else if (backupControl.getBlinkState() == BackupControl.BLINK_RELEASE){
                        car.setCommand(Command.r);
                    }

//                    -=THIS USES NUMBER OF BLINKS=-
//                    if(backupControl.getNumberOfBlinks() == 2){
//                        car.setCommand(Command.l);
//                    } else if (backupControl.getNumberOfBlinks() == 3){
//                        car.setCommand(Command.r);
//                    }
//                    backupControl.setNumberOfBlinks(0);

                    break;

                case TGDevice.MSG_RAW_DATA:

                    MessageParser.parseRawData(msg, eeg);

                    if (times == 0) {
                        Log.i("Time start ", System.currentTimeMillis() + "");
                        pattern.add(eeg);
                        ComparePatterns compPatt = new ComparePatterns(pattern.toArray(), neuralNetwork);
                        String send = compPatt.compare(databaseAccess);
                        Log.i("Send message ", send);
                        Connected.write(send);

                        eeg = new Eeg();
                        times = 1000;
                        Log.i("Time stop ", System.currentTimeMillis() + "");
                    } else {
                        times--;
                    }

                    break;

            }
        }
    };

    public void testComparePatterns() {
        LinkedList<double[]> patternList = new LinkedList<>();

        double[] d1 = {1, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8};
        double[] d2 = {2, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9};
        double[] d3 = {3, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10};
        double[] d4 = {4, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11};

        patternList.add(d1);
        patternList.add(d2);
        patternList.add(d3);
        patternList.add(d4);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
  //      Action viewAction = Action.newAction(
   //             Action.TYPE_VIEW, // TODO: choose an action type.
   //             "User Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
   //             Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
     //           Uri.parse("android-app://mindcar.testing.ui/http/host/path")
       // );
       // AppIndex.AppIndexApi.end(client, viewAction);
        //client.disconnect();
   // }

        TrainingSet dataSet = NeuralNetworkHelper.createTrainingSet(patternList, patternList.get(0).length, 4);
        NeuralNetwork testNetwork = NeuralNetworkHelper.createNetwork(dataSet, patternList.get(0).length, 4);

        start.setText("Stop");
        for (int i = 0; i < 10000000; i++) {
            continue;
        }

        double[] t1 = {1, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8, 2, 3, 4, 5, 6, 7, 8};
        double[] t2 = {2, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9, 3, 4, 5, 6, 7, 8, 9};
        double[] t3 = {3, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10};
        double[] t4 = {4, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11};

        ComparePatterns test1 = new ComparePatterns(t1, testNetwork );
        Log.i("Test", test1.compare(databaseAccess));

        ComparePatterns test2 = new ComparePatterns(t2, testNetwork );
        Log.i("Test", test2.compare(databaseAccess));
        ComparePatterns test3 = new ComparePatterns(t3, testNetwork );
        Log.i("Test", test3.compare(databaseAccess));
        ComparePatterns test4 = new ComparePatterns(t4, testNetwork );
        Log.i("Test", test4.compare(databaseAccess));
        start.setText("Start");

    }

}