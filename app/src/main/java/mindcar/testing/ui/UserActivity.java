package mindcar.testing.ui;
import android.app.Activity;
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
import mindcar.testing.util.NeuralNetworkHelper;

// Madisen & Nikos & Sanja & Mattias
//This class handles the main view while controlling the car
public class UserActivity extends Activity implements View.OnClickListener {

    public static Boolean appRunning = false;//used by Bluetooth to start/stop communication with the car

    Button userSettings;
    public static NeuralNetwork neuralNetwork;
    public static TrainingSet trainingSet;

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

        //Madisen
        iv = (ImageView) findViewById(R.id.profile_image_view);

        //Madisen & Sanja & Mattias
            //Handling of button clicks
        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (toggle.isChecked()) {
                    appRunning = true;
                    if(mindoption.isChecked()) {
                        toggle.setText("Loading");
                        if(neuralNetwork == null){
                            neuralNetwork = RegisterPatternActivity.neuralNetwork;
                        }
                        if(trainingSet == null){
                            trainingSet = RegisterPatternActivity.trainingSet;
                        }
                        if(neuralNetwork.getLearningThread() == null) {
                            neuralNetwork.learnInNewThread(trainingSet);
                        }
                        toggle.setText("Pause");
                    }
                } else {
                    appRunning = false;
                    BluetoothActivity.connected.write("STOP");
                }

            }
        });


        System.out.println("The user name passed to UserActivity from StartActivity is: " + name);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
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
                this.finish();
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