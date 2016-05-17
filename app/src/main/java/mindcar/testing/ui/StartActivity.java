package mindcar.testing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import mindcar.testing.ui.dev.DeveloperActivity;
import mindcar.testing.util.DatabaseAccess;
import mindcar.testing.R;
import mindcar.testing.util.UserData;

import static mindcar.testing.util.UserData.*;

public class StartActivity extends Activity implements View.OnClickListener {

    Button bLogin;
    EditText ET_USER_NAME, ET_PASSWORD;
    Button bSIGNUP;
    TextView devView;

    //nikos
    Button userSettings;

    public static String un, pw;


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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                if (databaseAccess.checkUser(ET_USER_NAME.getText().toString(), ET_PASSWORD.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    //databaseAccess.close();
                    //This value will be passed to User activity to fetch the name and photo
                    //of whoever logs in to the app
                    un = ET_USER_NAME.getText().toString();
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.yes);
                    mp.start();
                    startActivity(new Intent(this, UserActivity.class));
                    //startActivity(new Intent(this, oldConnection.class));
                }
                else {

                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                    MediaPlayer mp2 = MediaPlayer.create(this, R.raw.no);
                    mp2.start();
                }
                break;

            case R.id.bSIGNUP:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            case R.id.devView:
                startActivity(new Intent(this, DeveloperActivity.class));
                break;

        }

    }
}
