package mindcar.testing.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.neurosky.thinkgear.TGDevice;

import mindcar.testing.R;
import mindcar.testing.objects.Command;
import mindcar.testing.objects.Connected;
import mindcar.testing.objects.Eeg;
import mindcar.testing.objects.Pattern;
import mindcar.testing.objects.SmartCar;
import mindcar.testing.util.CommandUtils;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.MessageParser;

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


/**
 * Created by madiseniman on 07/04/16.
 */
public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartCar car;
    private Eeg eeg;
    private TGDevice tgDevice;
    private Pattern<Eeg> pattern;
    private Command x;

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
        setContentView(R.layout.activity_user);
        /*pattern = new Pattern<>();
        car = new SmartCar();
        x = car.getCommands();
        tgDevice = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
        tgDevice.start();*/

        logout = (Button) findViewById(R.id.logout);
        displayName(v);
        //username.setVisibility(View.VISIBLE);
        System.out.println("The user name passed to UserActivity from StartActivity is: " + name);
        logout.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.logout:
                startActivity(new Intent(this, StartActivity.class));
        }
    }


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
    /*private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (car.getCommands() != x) {
                Connected.write(CommandUtils.toByteArray(car.getCommands()));
            }
            if (msg.what == TGDevice.MSG_RAW_MULTI) {
                eeg = new Eeg();
                MessageParser.parseMessage(msg, eeg);
                pattern.add(eeg);
                x = car.getCommands();
            }
        }*/


    };

    //@Override
    //public void onStart() {
      //  super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      //  client.connect();
      //  Action viewAction = Action.newAction(
        //        Action.TYPE_VIEW, // TODO: choose an action type.
          //      "User Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
            //    Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
              //  Uri.parse("android-app://mindcar.testing.ui/http/host/path")
       // );
       // AppIndex.AppIndexApi.start(client, viewAction);
    //}

   // @Override
   // public void onStop() {
    //    super.onStop();

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



