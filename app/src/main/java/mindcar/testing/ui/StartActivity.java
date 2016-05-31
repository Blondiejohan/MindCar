package mindcar.testing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import mindcar.testing.R;
import mindcar.testing.ui.dev.DeveloperActivity;
import mindcar.testing.ui.dev.edit.EditUsersActivity;
import mindcar.testing.util.DatabaseAccess;

//Sanja & Mattias
    //This class handles login, allows the options to register a new account
    // and enter a developer view.
public class StartActivity extends Activity implements View.OnClickListener {

    //Sanja
        //Connection to the layout xml
    Button bLogin;
    EditText ET_USER_NAME, ET_PASSWORD;
    Button bSIGNUP;
    TextView devView;

    //Sanja
        //Creates all buttons and fields
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ET_USER_NAME = (EditText) findViewById(R.id.ET_USER_NAME);
        ET_PASSWORD = (EditText) findViewById(R.id.ET_PASSWORD);
        bLogin = (Button) findViewById(R.id.bLogin);
        bSIGNUP = (Button) findViewById(R.id.bSIGNUP);
        devView = (TextView) findViewById(R.id.devView);


        bLogin.setOnClickListener(this);
        bSIGNUP.setOnClickListener(this);
        devView.setOnClickListener(this);
    }

    //Sanja
        // Handles the different button clicks.
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Sanja
                //Log in button clicked
            case R.id.bLogin:
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                if(databaseAccess.isDeveloper(ET_USER_NAME.getText().toString(), ET_PASSWORD.getText().toString())) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.yes);
                    mp.start();

                    startActivity(new Intent(this, EditUsersActivity.class));
                    this.finish();
                    break;
                } else if (databaseAccess.checkUser(ET_USER_NAME.getText().toString(), ET_PASSWORD.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                    //This value will be passed to User activity to fetch the name and photo
                    //of whoever logs in to the app
                    saveInfo();

                    //Sanja
                    //Play positive sound when successful login.
                    //User is sent to the main use screen
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.yes);
                    mp.start();

                    startActivity(new Intent(this, UserActivity.class));
                    this.finish();
                    break;
                }
                else {
                    //Sanja
                     //Play negative sound when failed to login.
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                    MediaPlayer mp2 = MediaPlayer.create(this, R.raw.no);
                    mp2.start();
                }
                break;
            //Sanja
                //New registration clicked
            case R.id.bSIGNUP:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            //Mattias
                //Developer view selected
            case R.id.devView:
                startActivity(new Intent(this, DeveloperActivity.class));
                this.finish();
                break;
        }

    }
    //Madisen
     //What does it do??
    public void saveInfo(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", ET_USER_NAME.getText().toString());
        editor.putString("password", ET_PASSWORD.getText().toString());
        editor.apply();

        Toast.makeText(this, "User Info Saved", Toast.LENGTH_LONG).show();
    }
}
