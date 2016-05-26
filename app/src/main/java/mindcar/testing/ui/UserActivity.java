package mindcar.testing.ui;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.common.io.Files;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingSet;

import java.io.File;
import java.io.IOException;

import mindcar.testing.R;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.util.NeuralNetworkHelper;

// Madisen & Nikos & Sanja & Mattias
//This class handles the main view while controlling the car
public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    public static Boolean appRunning = false;//used by Bluetooth to start/stop communication with the car

    Button userSettings;
    public static NeuralNetwork neuralNetwork;
    public static DatabaseAccess databaseAccess;

    //Variables from StartsActivity
    String name = null;
    String password = null;
    //Madisen
    View v;
    ImageView iv;
    TextView username;
    Button logout;
    ImageView userPic;
    static ImageView direction;
    static TextView directionText;
    Bitmap finalPic;
    ToggleButton toggle;
    //Nikos
    RadioButton mindoption;
    RadioButton attentionoption;
    RadioButton blinkoption;
    TextView blinkInstructions, attentionInstructions;
    //Sanja
    public static boolean mindControl = true;
    public static boolean attentionControl = false;
    public static boolean blinkControl = false;

    public static boolean loaded = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Nikos & Madisen & Sanja & Johan
        userSettings = (Button) findViewById(R.id.userSettings);
        logout = (Button) findViewById(R.id.logout);
        mindoption = (RadioButton) findViewById(R.id.mindoption);
        attentionoption = (RadioButton) findViewById(R.id.attentionoption);
        blinkoption = (RadioButton) findViewById(R.id.blinksoption);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        blinkInstructions = (TextView) findViewById(R.id.blinkInstructions);
        directionText = (TextView) findViewById(R.id.directionText);
        direction = (ImageView) findViewById(R.id.direction);
        attentionInstructions = (TextView) findViewById(R.id.attentionInstructions);
        userSettings.setOnClickListener(this);
        databaseAccess = DatabaseAccess.getInstance(this);

        //Madisen
        iv = (ImageView) findViewById(R.id.profile_image_view);
        getUNPW();

        //Madisen & Sanja & Mattias
            //Handling of button clicks
        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (toggle.isChecked()) {
                    appRunning = true;
                    if(mindoption.isChecked()) {
                        toggle.setText("Loading");
                        if(neuralNetwork == null){
                            databaseAccess.open();
                            byte[] bytes = databaseAccess.getNetwork(UserActivity.this, name);
                            neuralNetwork = NeuralNetworkHelper.loadNetwork(UserActivity.this, bytes);
                            databaseAccess.close();
                        }
                        neuralNetwork.resumeLearning();
                        toggle.setText("Pause");
                    }
                } else {
                    appRunning = false;
                    BluetoothActivity.connected.write("STOP");
                }

            }
        });

        //Madisen
        displayName(v);
        displayPhoto(iv);
        username.setVisibility(View.VISIBLE);
        System.out.println("The user name passed to UserActivity from StartActivity is: " + name);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    //Madisen
        //get user name and password from global settings
    public void getUNPW(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        name = sharedPref.getString("username", "");
        password = sharedPref.getString("password","");
    }

    //Madisen
    public void displayName(View view) {
        username = (TextView) findViewById(R.id.username);
        username.setText(name);
    }

    //Madisen
    public void displayPhoto(ImageView p) {
        userPic = iv;
        byte[] image;
        System.out.println("The username that gets sent to getPhoto from displayPhoto is " + name);
        databaseAccess.open();
        image = databaseAccess.getPhoto(name);
        if (image != null) {
            finalPic = getImage(image);
            Drawable d = new BitmapDrawable(finalPic);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                userPic.setBackground(d);
            }
        }
        databaseAccess.close();
        System.out.println("Finnished photo stuff");
    }

    //Madisen
     // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //Madisen & Nikos & Sanja
        //Button handling
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout: //logout button
                neuralNetwork.stopLearning();
                try {
                    NeuralNetworkHelper.saveNetwork(UserActivity.this, neuralNetwork, name);
                    File nnet = UserActivity.this.getFileStreamPath(name + ".nnet");
                    byte[] b = Files.toByteArray(nnet);
                    databaseAccess.open();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("neuralnetwork", b);
                    databaseAccess.update("Users", contentValues, name);
                    databaseAccess.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                neuralNetwork = null;
                startActivity(new Intent(this, StartActivity.class));
                this.finish();
                break;
            case R.id.userSettings: //user settings password for changing personal info
                startActivity(new Intent(this, UserSettings.class));
                break;
            case R.id.mindoption: //radio button options
                mindControl = true;
                attentionControl = false;
                blinkControl = false;
                mindoption.setChecked(true);
                attentionInstructions.setVisibility(View.INVISIBLE);
                blinkInstructions.setVisibility(View.INVISIBLE);
                break;
            case R.id.attentionoption://radio button options
                mindControl = false;
                attentionControl = true;
                blinkControl = false;
                attentionoption.setChecked(true);
                attentionInstructions.setVisibility(View.VISIBLE);
                blinkInstructions.setVisibility(View.INVISIBLE);
                break;
            case R.id.blinksoption://radio button options
                mindControl = false;
                attentionControl = false;
                blinkControl = true;
                blinkoption.setChecked(true);
                attentionInstructions.setVisibility(View.VISIBLE);
                blinkInstructions.setVisibility(View.VISIBLE);
                break;
        }
    }
}